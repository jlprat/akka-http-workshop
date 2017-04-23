package io.github.jlprat.akka.http.workshop.ex4;

import akka.dispatch.MessageDispatcher;
import akka.http.javadsl.model.*;
import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;
import akka.stream.javadsl.StreamConverters;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This class showcases how to deal with blocking
 * Created by @jlprat on 21/04/2017.
 */
public class BlockingExampleJava extends HttpApp {
    @Override
    protected Route route() {
        return null;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new BlockingExampleJava().startServer("localhost", 9000);
    }
}
