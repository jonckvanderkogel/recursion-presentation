## (Tail) recursion in the JVM
This project discusses head recursion vs tail recursion. It demonstrates why in some situations due to the nature of
the data structure, a recursive solution is much preferred over an iterative one. Next it shows how a regular head
recursive function will eventually produce a StackOverflowError.

After that we move to how the Kotlin compiler applies the tail-call optimization, and we take a look at the 
difference on a bytecode level when we compare the exact same code except for one of the functions having the 
tail-call optimization applied and the other does not.

And to conclude we will discuss how we can also use tail-recursion in Java.

To see the decompiled code of the Kotlin class NetworkAnalysis.class: Tools > Kotlin > Show Kotlin bytecode
Then hit decompile.  We can then study the differences between the two functions.

The benchmark results between the iterative version of the algorithm and the tail-recursive one:
```
/Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=60642:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/jonck/Documents/dev/code/tail-recursion-presentation/target/classes:/Applications/maven/repository/io/vavr/vavr/0.10.4/vavr-0.10.4.jar:/Applications/maven/repository/io/vavr/vavr-match/0.10.4/vavr-match-0.10.4.jar:/Applications/maven/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/Applications/maven/repository/ch/qos/logback/logback-core/1.2.7/logback-core-1.2.7.jar:/Applications/maven/repository/ch/qos/logback/logback-classic/1.2.7/logback-classic-1.2.7.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.5.10/kotlin-stdlib-jdk8-1.5.10.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib/1.5.10/kotlin-stdlib-1.5.10.jar:/Applications/maven/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.5.10/kotlin-stdlib-common-1.5.10.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.5.10/kotlin-stdlib-jdk7-1.5.10.jar:/Applications/maven/repository/org/openjdk/jmh/jmh-core/1.33/jmh-core-1.33.jar:/Applications/maven/repository/net/sf/jopt-simple/jopt-simple/4.6/jopt-simple-4.6.jar:/Applications/maven/repository/org/apache/commons/commons-math3/3.2/commons-math3-3.2.jar:/Applications/maven/repository/org/openjdk/jmh/jmh-generator-annprocess/1.33/jmh-generator-annprocess-1.33.jar benchmarking.BenchmarkRunner
# JMH version: 1.33
# VM version: JDK 16.0.1, OpenJDK 64-Bit Server VM, 16.0.1+9
# VM invoker: /Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java
# VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=60642:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: full + dont-inline hint (default, use -Djmh.blackhole.autoDetect=true to auto-detect)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: benchmarking.NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis

# Run progress: 0,00% complete, ETA 00:16:40
# Fork: 1 of 5
# Warmup Iteration   1: 3,448 ops/s
# Warmup Iteration   2: 3,434 ops/s
# Warmup Iteration   3: 3,461 ops/s
# Warmup Iteration   4: 3,420 ops/s
# Warmup Iteration   5: 3,351 ops/s
Iteration   1: 3,415 ops/s
Iteration   2: 3,353 ops/s
Iteration   3: 3,392 ops/s
Iteration   4: 3,377 ops/s
Iteration   5: 3,463 ops/s

# Run progress: 10,00% complete, ETA 00:15:47
# Fork: 2 of 5
# Warmup Iteration   1: 3,375 ops/s
# Warmup Iteration   2: 3,452 ops/s
# Warmup Iteration   3: 3,217 ops/s
# Warmup Iteration   4: 3,251 ops/s
# Warmup Iteration   5: 3,347 ops/s
Iteration   1: 3,301 ops/s
Iteration   2: 3,284 ops/s
Iteration   3: 3,289 ops/s
Iteration   4: 3,279 ops/s
Iteration   5: 3,250 ops/s

# Run progress: 20,00% complete, ETA 00:13:59
# Fork: 3 of 5
# Warmup Iteration   1: 3,637 ops/s
# Warmup Iteration   2: 3,646 ops/s
# Warmup Iteration   3: 3,728 ops/s
# Warmup Iteration   4: 3,823 ops/s
# Warmup Iteration   5: 3,647 ops/s
Iteration   1: 3,656 ops/s
Iteration   2: 3,699 ops/s
Iteration   3: 3,691 ops/s
Iteration   4: 3,710 ops/s
Iteration   5: 3,665 ops/s

# Run progress: 30,00% complete, ETA 00:12:15
# Fork: 4 of 5
# Warmup Iteration   1: 3,124 ops/s
# Warmup Iteration   2: 3,075 ops/s
# Warmup Iteration   3: 3,110 ops/s
# Warmup Iteration   4: 3,079 ops/s
# Warmup Iteration   5: 3,154 ops/s
Iteration   1: 3,088 ops/s
Iteration   2: 3,068 ops/s
Iteration   3: 3,127 ops/s
Iteration   4: 3,119 ops/s
Iteration   5: 3,096 ops/s

# Run progress: 40,00% complete, ETA 00:10:29
# Fork: 5 of 5
# Warmup Iteration   1: 3,068 ops/s
# Warmup Iteration   2: 3,065 ops/s
# Warmup Iteration   3: 3,009 ops/s
# Warmup Iteration   4: 3,043 ops/s
# Warmup Iteration   5: 2,994 ops/s
Iteration   1: 2,987 ops/s
Iteration   2: 3,015 ops/s
Iteration   3: 3,019 ops/s
Iteration   4: 3,083 ops/s
Iteration   5: 3,043 ops/s


Result "benchmarking.NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis":
  3,299 ±(99.9%) 0,179 ops/s [Average]
  (min, avg, max) = (2,987, 3,299, 3,710), stdev = 0,239
  CI (99.9%): [3,119, 3,478] (assumes normal distribution)


# JMH version: 1.33
# VM version: JDK 16.0.1, OpenJDK 64-Bit Server VM, 16.0.1+9
# VM invoker: /Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java
# VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=60642:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: full + dont-inline hint (default, use -Djmh.blackhole.autoDetect=true to auto-detect)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: benchmarking.NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis

# Run progress: 50,00% complete, ETA 00:08:44
# Fork: 1 of 5
# Warmup Iteration   1: 3,229 ops/s
# Warmup Iteration   2: 3,206 ops/s
# Warmup Iteration   3: 3,172 ops/s
# Warmup Iteration   4: 3,229 ops/s
# Warmup Iteration   5: 3,239 ops/s
Iteration   1: 3,199 ops/s
Iteration   2: 3,226 ops/s
Iteration   3: 3,190 ops/s
Iteration   4: 3,118 ops/s
Iteration   5: 3,177 ops/s

# Run progress: 60,00% complete, ETA 00:06:59
# Fork: 2 of 5
# Warmup Iteration   1: 3,525 ops/s
# Warmup Iteration   2: 3,648 ops/s
# Warmup Iteration   3: 3,568 ops/s
# Warmup Iteration   4: 3,611 ops/s
# Warmup Iteration   5: 3,617 ops/s
Iteration   1: 3,638 ops/s
Iteration   2: 3,630 ops/s
Iteration   3: 3,526 ops/s
Iteration   4: 3,682 ops/s
Iteration   5: 3,653 ops/s

# Run progress: 70,00% complete, ETA 00:05:15
# Fork: 3 of 5
# Warmup Iteration   1: 3,354 ops/s
# Warmup Iteration   2: 3,340 ops/s
# Warmup Iteration   3: 3,290 ops/s
# Warmup Iteration   4: 3,313 ops/s
# Warmup Iteration   5: 3,331 ops/s
Iteration   1: 3,374 ops/s
Iteration   2: 3,336 ops/s
Iteration   3: 3,410 ops/s
Iteration   4: 3,221 ops/s
Iteration   5: 3,129 ops/s

# Run progress: 80,00% complete, ETA 00:03:30
# Fork: 4 of 5
# Warmup Iteration   1: 2,991 ops/s
# Warmup Iteration   2: 3,190 ops/s
# Warmup Iteration   3: 3,106 ops/s
# Warmup Iteration   4: 3,025 ops/s
# Warmup Iteration   5: 3,151 ops/s
Iteration   1: 3,065 ops/s
Iteration   2: 3,112 ops/s
Iteration   3: 3,120 ops/s
Iteration   4: 3,145 ops/s
Iteration   5: 3,056 ops/s

# Run progress: 90,00% complete, ETA 00:01:45
# Fork: 5 of 5
# Warmup Iteration   1: 3,196 ops/s
# Warmup Iteration   2: 3,322 ops/s
# Warmup Iteration   3: 3,278 ops/s
# Warmup Iteration   4: 3,130 ops/s
# Warmup Iteration   5: 3,270 ops/s
Iteration   1: 3,295 ops/s
Iteration   2: 3,279 ops/s
Iteration   3: 3,287 ops/s
Iteration   4: 3,082 ops/s
Iteration   5: 3,130 ops/s


Result "benchmarking.NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis":
  3,283 ±(99.9%) 0,149 ops/s [Average]
  (min, avg, max) = (3,056, 3,283, 3,682), stdev = 0,199
  CI (99.9%): [3,134, 3,432] (assumes normal distribution)


# Run complete. Total time: 00:17:29

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                                        Mode  Cnt  Score   Error  Units
NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis      thrpt   25  3,299 ± 0,179  ops/s
NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis  thrpt   25  3,283 ± 0,149  ops/s

Process finished with exit code 0
```
