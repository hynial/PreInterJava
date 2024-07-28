package com.hynial.preinter.tool;

import com.hynial.tool.ExecuteSysCommander;
import org.junit.jupiter.api.Test;

public class ExecuteSysCommanderTest {
    @Test
    void test() throws Exception {
        ExecuteSysCommander.ExeResult r0 = ExecuteSysCommander.go("ls -l / | grep \"es\"", ".", s -> {
            System.out.println("------:" + s);
        });

        System.out.println(r0);

        String path = "/";
        String cmd = "df -i " + path + " | awk 'NR==2 {print $5}'";
        ExecuteSysCommander.ExeResult r = ExecuteSysCommander.go(cmd, ".");

        System.out.println(r);
    }
}
