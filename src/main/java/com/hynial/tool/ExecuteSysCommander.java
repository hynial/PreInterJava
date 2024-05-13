package com.hynial.tool;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ExecuteSysCommander {
    public static String SYS_ENCODE = "UTF-8";
    public static String LINE_SEPARATE = "\n";

    public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() - 1,
            Runtime.getRuntime().availableProcessors(),
            100L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.CallerRunsPolicy());


    public static ExeResult go(String cmd, String workDir) throws Exception {
        return new ExeProcess(cmd, workDir).callProcessBuilder(null);
    }

    public static ExeResult go(String cmd, String workDir, Consumer<String> stringConsumer) throws Exception {
        return new ExeProcess(cmd, workDir).callProcessBuilder(stringConsumer);
    }

    public static class ExeResult {
        private int code;
        private String msg;

        private String cmd;
        private String cwd;

        public static Builder Builder() {
            return new Builder();
        }
        private static class Builder {
            private ExeResult exeResult;

            public Builder() {
                this.exeResult = new ExeResult();
            }

            public Builder code(int code) {
                this.exeResult.code = code;
                return this;
            }

            public Builder msg(String msg) {
                this.exeResult.msg = msg;
                return this;
            }

            public Builder cmd(String cmd) {
                this.exeResult.cmd = cmd;
                return this;
            }

            public Builder cwd(String cwd) {
                this.exeResult.cwd = cwd;
                return this;
            }

            public ExeResult build() {
                return this.exeResult;
            }
        }

        @Override
        public String toString() {
            return new StringBuilder().append("cmd:").append(cmd).append("\n")
                    .append("cwd:").append(cwd).append("\n")
                    .append("code:").append(code).append("\n")
                    .append("msg:").append("\n").append(msg).append("\n")
                    .toString();
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static class ExeProcess {
        private List<String> cmdList;
        private String workDir;

        public ExeProcess(List<String> cmdList, String workDir) {
            this.cmdList = cmdList;
            this.workDir = workDir;
        }

        public ExeProcess(String cmd, String workDir) {
            if (StringUtils.isEmpty(cmd)) {
                print("CommandIsEmpty");
                throw new IllegalArgumentException("CommandIsEmpty");
            }

            String[] commandSplit = cmd.split("\\s+");
            List<String> cmdList = new ArrayList<>();

            for (int i = 0; i < commandSplit.length; i++) {
                cmdList.add(commandSplit[i]);
            }

            this.cmdList = cmdList;
            this.workDir = workDir;
        }

        public ExeResult callProcessBuilder(Consumer<String> successConsumer) throws InterruptedException, IOException {
            String cmd = this.cmdList.stream().collect(Collectors.joining(" "));
            print(String.format("callCommand:%s", cmd));

            ProcessBuilder builder = new ProcessBuilder();
            if (isWindows) {
                builder.command("cmd.exe", "/c", cmd);
            } else {
                builder.command("sh", "-c", cmd); // compatible with Pipe commands. e.g. String cmd = "df -i " + path + " | awk 'NR==2 {print $5}'";
                // builder.command(this.cmdList);
            }

            String cwd = this.workDir == null ? System.getProperty("user.home") : this.workDir;
            File w = new File(cwd);
            cwd = w.getAbsolutePath();
            builder.directory(w);
            Process process = builder.start();

            StringBuilder successBuilder = new StringBuilder("");
            StreamGobbler streamGobbler1 = new StreamGobbler(process.getInputStream(), successConsumer != null ? successConsumer : s -> {
                successBuilder.append(s).append(LINE_SEPARATE);
            });

            if(successBuilder.toString().endsWith(LINE_SEPARATE)){
                successBuilder.substring(0, successBuilder.toString().lastIndexOf(LINE_SEPARATE));
            }

            StringBuilder errorBuilder = new StringBuilder("");

            Future<?> future1 = executorService.submit(streamGobbler1);

            StreamGobbler streamGobbler2 = new StreamGobbler(process.getErrorStream(), err -> {
                errorBuilder.append(err).append(LINE_SEPARATE);
            });

            if(errorBuilder.toString().endsWith(LINE_SEPARATE)){
                errorBuilder.substring(0, errorBuilder.toString().lastIndexOf(LINE_SEPARATE));
            }

            Future<?> future2 = executorService.submit(streamGobbler2);

            int exitCode = process.waitFor();

            return ExeResult.Builder().cmd(cmd).code(exitCode).cwd(cwd).msg(exitCode == 0 ? successBuilder.toString() : errorBuilder.toString()).build();
        }
    }

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            try {
                new BufferedReader(new InputStreamReader(inputStream, SYS_ENCODE)).lines().forEach(consumer);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
