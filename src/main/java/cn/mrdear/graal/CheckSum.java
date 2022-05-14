package cn.mrdear.graal;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.concurrent.Callable;

/**
 * @author quding
 * @since ${DATE}
 */
@picocli.CommandLine.Command(name = "checksum", mixinStandardHelpOptions = true, version = "checksum 4.0",
    description = "Prints the checksum (SHA-256 by default) of a file to STDOUT.")
public class CheckSum implements Callable<Integer> {

    @picocli.CommandLine.Parameters(index = "0", description = "The file whose checksum to calculate.")
    private File file;

    @picocli.CommandLine.Option(names = {"-a", "--algorithm"}, description = "MD5, SHA-1, SHA-256, ...")
    private String algorithm = "SHA-256";

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        byte[] fileContents = Files.readAllBytes(file.toPath());
        byte[] digest = MessageDigest.getInstance(algorithm).digest(fileContents);
        System.out.printf("%0" + (digest.length*2) + "x%n", new BigInteger(1, digest));
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        int exitCode = new CommandLine(new CheckSum()).execute(args);
        System.exit(exitCode);
    }
}