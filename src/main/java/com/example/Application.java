//package com.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import jakarta.annotation.PostConstruct;
//
//import java.io.PrintStream;
//import java.io.OutputStream;
//
//@SpringBootApplication
//public class Application {
//    
//    private static final PrintStream originalErr = System.err;
//    private static boolean errorCaptured = false;
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//    public static void main(String[] args) {
//        // Suppress ALL console output during startup (except banner)
//        System.setProperty("logging.level.root", "OFF");
//        System.setProperty("logging.level.org.springframework", "OFF");
//        
//        SpringApplication app = new SpringApplication(Application.class);
//        app.setBannerMode(org.springframework.boot.Banner.Mode.CONSOLE);
//        app.setLogStartupInfo(false);
//        
//        // Redirect System.err to suppress stack traces
//        PrintStream dummyStream = new PrintStream(new OutputStream() {
//            @Override
//            public void write(int b) {
//                // Silently discard
//            }
//        });
//        
//        try {
//            // Show banner first
//            System.err.flush();
//            
//            // Then suppress error output
//            System.setErr(dummyStream);
//            
//            app.run(args);
//            
//            // Restore if successful
//            System.setErr(originalErr);
//            
//        } catch (Throwable ex) {
//            // Restore System.err before printing
//            System.setErr(originalErr);
//            printCleanError(ex);
//            System.exit(1);
//        }
//    }
//    
//    private static void printCleanError(Throwable ex) {
//        Throwable root = getRootCause(ex);
//        
//        String line = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
//        
//        originalErr.println("\n" + line);
//        originalErr.println("🔥 APPLICATION FAILED TO START");
//        originalErr.println(line);
//        originalErr.println("Error Type: " + root.getClass().getSimpleName());
//        originalErr.println("Message: " + root.getMessage());
//        
//        // Find the most relevant location (your code, not framework)
//        String location = findRelevantLocation(ex);
//        if (location != null) {
//            originalErr.println("Location: " + location);
//        }
//        
//        originalErr.println(line);
//        originalErr.println("\n💡 Fix the issue above and restart the application.\n");
//    }
//    
//    private static Throwable getRootCause(Throwable ex) {
//        Throwable root = ex;
//        while (root.getCause() != null && root.getCause() != root) {
//            root = root.getCause();
//        }
//        return root;
//    }
//    
//    private static String findRelevantLocation(Throwable ex) {
//        // Walk through the exception chain to find your code
//        Throwable current = ex;
//        while (current != null) {
//            for (StackTraceElement element : current.getStackTrace()) {
//                String className = element.getClassName();
//                // Look for your package (com.example) and skip framework classes
//                if (className.startsWith("com.example") && 
//                    !className.contains("$$") && // Skip proxy classes
//                    !className.equals("com.example.Application") && // Skip Application.java
//                    element.getLineNumber() > 0) {
//                    return className + "." + element.getMethodName() + 
//                           "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
//                }
//            }
//            current = current.getCause();
//        }
//        return null;
//    }
//    
//    
//
//    
//}


package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        // ✅ IMPORTANT: Render PORT binding
        String port = System.getenv("PORT");
        if (port != null) {
            System.getProperties().put("server.port", port);
        } else {
            System.getProperties().put("server.port", "8080");
        }

        SpringApplication.run(Application.class, args);
    }
}