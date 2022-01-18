package benchmarking;

import network.NetworkAnalysisIterative;
import network.NetworkAnalysisTailRecursive;
import network.Node;
import network.setup.SetupNetwork;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

public class NetworkAnalysisBenchmark {
    @State(Scope.Benchmark)
    public static class InitializedNetwork {
        public Node root = new SetupNetwork().setupNetwork(50000);
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkIterativeNetworkAnalysis(Blackhole blackhole, InitializedNetwork state) {
        blackhole.consume(NetworkAnalysisIterative.calculateSizeOfNetworkIterative(state.root));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void benchmarkTailRecursiveNetworkAnalysis(Blackhole blackhole, InitializedNetwork state) {
        blackhole.consume(NetworkAnalysisTailRecursive.calculateSizeOfNetwork(state.root));
    }
}
