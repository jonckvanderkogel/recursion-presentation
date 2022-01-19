package benchmarking;

import network.NetworkAnalysisHeadRecursive;
import network.NetworkAnalysisIterative;
import network.NetworkAnalysisTailRecursive;
import network.Node;
import network.setup.SetupNetwork;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

public class NetworkAnalysisBenchmark {
    @State(Scope.Benchmark)
    public static class InitializedNetwork {
        public Node root = new SetupNetwork().setupNetwork(5000);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkIterativeNetworkAnalysis(Blackhole blackhole, InitializedNetwork state) {
        blackhole.consume(NetworkAnalysisIterative.calculateSizeOfNetwork(state.root));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkHeadRecursiveNetworkAnalysis(Blackhole blackhole, InitializedNetwork state) {
        blackhole.consume(NetworkAnalysisHeadRecursive.calculateSizeOfNetwork(state.root));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkTailRecursiveNetworkAnalysis(Blackhole blackhole, InitializedNetwork state) {
        blackhole.consume(NetworkAnalysisTailRecursive.calculateSizeOfNetwork(state.root));
    }
}
