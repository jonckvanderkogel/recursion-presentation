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
/Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63663:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/jonck/Documents/dev/code/tail-recursion-presentation/target/classes:/Applications/maven/repository/io/vavr/vavr/0.10.4/vavr-0.10.4.jar:/Applications/maven/repository/io/vavr/vavr-match/0.10.4/vavr-match-0.10.4.jar:/Applications/maven/repository/org/slf4j/slf4j-api/1.7.32/slf4j-api-1.7.32.jar:/Applications/maven/repository/ch/qos/logback/logback-core/1.2.7/logback-core-1.2.7.jar:/Applications/maven/repository/ch/qos/logback/logback-classic/1.2.7/logback-classic-1.2.7.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk8/1.5.10/kotlin-stdlib-jdk8-1.5.10.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib/1.5.10/kotlin-stdlib-1.5.10.jar:/Applications/maven/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-common/1.5.10/kotlin-stdlib-common-1.5.10.jar:/Applications/maven/repository/org/jetbrains/kotlin/kotlin-stdlib-jdk7/1.5.10/kotlin-stdlib-jdk7-1.5.10.jar:/Applications/maven/repository/org/openjdk/jmh/jmh-core/1.33/jmh-core-1.33.jar:/Applications/maven/repository/net/sf/jopt-simple/jopt-simple/4.6/jopt-simple-4.6.jar:/Applications/maven/repository/org/apache/commons/commons-math3/3.2/commons-math3-3.2.jar:/Applications/maven/repository/org/openjdk/jmh/jmh-generator-annprocess/1.33/jmh-generator-annprocess-1.33.jar benchmarking.BenchmarkRunner
# JMH version: 1.33
# VM version: JDK 16.0.1, OpenJDK 64-Bit Server VM, 16.0.1+9
# VM invoker: /Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java
# VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63663:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: full + dont-inline hint (default, use -Djmh.blackhole.autoDetect=true to auto-detect)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: benchmarking.NetworkAnalysisBenchmark.benchmarkHeadRecursiveNetworkAnalysis

# Run progress: 0,00% complete, ETA 00:25:00
# Fork: 1 of 5
# Warmup Iteration   1: 343,386 ops/s
# Warmup Iteration   2: 366,415 ops/s
# Warmup Iteration   3: 358,269 ops/s
# Warmup Iteration   4: 354,552 ops/s
# Warmup Iteration   5: 355,125 ops/s
Iteration   1: 361,440 ops/s
Iteration   2: 361,385 ops/s
Iteration   3: 363,753 ops/s
Iteration   4: 362,612 ops/s
Iteration   5: 360,302 ops/s

# Run progress: 6,67% complete, ETA 00:23:39
# Fork: 2 of 5
# Warmup Iteration   1: 307,555 ops/s
# Warmup Iteration   2: 314,351 ops/s
# Warmup Iteration   3: 319,219 ops/s
# Warmup Iteration   4: 319,056 ops/s
# Warmup Iteration   5: 320,982 ops/s
Iteration   1: 319,746 ops/s
Iteration   2: 321,983 ops/s
Iteration   3: 322,315 ops/s
Iteration   4: 320,973 ops/s
Iteration   5: 318,579 ops/s

# Run progress: 13,33% complete, ETA 00:21:55
# Fork: 3 of 5
# Warmup Iteration   1: 282,798 ops/s
# Warmup Iteration   2: 287,598 ops/s
# Warmup Iteration   3: 294,105 ops/s
# Warmup Iteration   4: 293,130 ops/s
# Warmup Iteration   5: 287,416 ops/s
Iteration   1: 294,993 ops/s
Iteration   2: 291,694 ops/s
Iteration   3: 289,527 ops/s
Iteration   4: 291,826 ops/s
Iteration   5: 289,994 ops/s

# Run progress: 20,00% complete, ETA 00:20:14
# Fork: 4 of 5
# Warmup Iteration   1: 301,401 ops/s
# Warmup Iteration   2: 314,954 ops/s
# Warmup Iteration   3: 315,634 ops/s
# Warmup Iteration   4: 314,356 ops/s
# Warmup Iteration   5: 314,719 ops/s
Iteration   1: 314,478 ops/s
Iteration   2: 312,464 ops/s
Iteration   3: 313,488 ops/s
Iteration   4: 313,713 ops/s
Iteration   5: 306,063 ops/s

# Run progress: 26,67% complete, ETA 00:18:32
# Fork: 5 of 5
# Warmup Iteration   1: 380,369 ops/s
# Warmup Iteration   2: 399,023 ops/s
# Warmup Iteration   3: 399,652 ops/s
# Warmup Iteration   4: 400,407 ops/s
# Warmup Iteration   5: 397,699 ops/s
Iteration   1: 372,171 ops/s
Iteration   2: 401,189 ops/s
Iteration   3: 403,582 ops/s
Iteration   4: 403,546 ops/s
Iteration   5: 380,501 ops/s


Result "benchmarking.NetworkAnalysisBenchmark.benchmarkHeadRecursiveNetworkAnalysis":
  335,693 ±(99.9%) 28,191 ops/s [Average]
  (min, avg, max) = (289,527, 335,693, 403,582), stdev = 37,635
  CI (99.9%): [307,501, 363,884] (assumes normal distribution)


# JMH version: 1.33
# VM version: JDK 16.0.1, OpenJDK 64-Bit Server VM, 16.0.1+9
# VM invoker: /Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java
# VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63663:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: full + dont-inline hint (default, use -Djmh.blackhole.autoDetect=true to auto-detect)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: benchmarking.NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis

# Run progress: 33,33% complete, ETA 00:16:51
# Fork: 1 of 5
# Warmup Iteration   1: 377,053 ops/s
# Warmup Iteration   2: 390,210 ops/s
# Warmup Iteration   3: 404,949 ops/s
# Warmup Iteration   4: 402,098 ops/s
# Warmup Iteration   5: 404,595 ops/s
Iteration   1: 401,617 ops/s
Iteration   2: 404,760 ops/s
Iteration   3: 403,582 ops/s
Iteration   4: 403,748 ops/s
Iteration   5: 401,604 ops/s

# Run progress: 40,00% complete, ETA 00:15:10
# Fork: 2 of 5
# Warmup Iteration   1: 355,333 ops/s
# Warmup Iteration   2: 373,178 ops/s
# Warmup Iteration   3: 373,067 ops/s
# Warmup Iteration   4: 370,105 ops/s
# Warmup Iteration   5: 375,781 ops/s
Iteration   1: 373,739 ops/s
Iteration   2: 372,888 ops/s
Iteration   3: 376,637 ops/s
Iteration   4: 376,246 ops/s
Iteration   5: 375,177 ops/s

# Run progress: 46,67% complete, ETA 00:13:29
# Fork: 3 of 5
# Warmup Iteration   1: 354,371 ops/s
# Warmup Iteration   2: 368,174 ops/s
# Warmup Iteration   3: 369,840 ops/s
# Warmup Iteration   4: 373,699 ops/s
# Warmup Iteration   5: 370,634 ops/s
Iteration   1: 370,211 ops/s
Iteration   2: 369,551 ops/s
Iteration   3: 373,714 ops/s
Iteration   4: 368,270 ops/s
Iteration   5: 369,613 ops/s

# Run progress: 53,33% complete, ETA 00:11:48
# Fork: 4 of 5
# Warmup Iteration   1: 272,010 ops/s
# Warmup Iteration   2: 282,801 ops/s
# Warmup Iteration   3: 285,199 ops/s
# Warmup Iteration   4: 287,019 ops/s
# Warmup Iteration   5: 284,920 ops/s
Iteration   1: 284,916 ops/s
Iteration   2: 286,882 ops/s
Iteration   3: 286,150 ops/s
Iteration   4: 284,347 ops/s
Iteration   5: 283,197 ops/s

# Run progress: 60,00% complete, ETA 00:10:07
# Fork: 5 of 5
# Warmup Iteration   1: 389,319 ops/s
# Warmup Iteration   2: 404,562 ops/s
# Warmup Iteration   3: 400,591 ops/s
# Warmup Iteration   4: 398,236 ops/s
# Warmup Iteration   5: 402,684 ops/s
Iteration   1: 402,097 ops/s
Iteration   2: 403,616 ops/s
Iteration   3: 401,229 ops/s
Iteration   4: 401,807 ops/s
Iteration   5: 389,140 ops/s


Result "benchmarking.NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis":
  366,589 ±(99.9%) 32,757 ops/s [Average]
  (min, avg, max) = (283,197, 366,589, 404,760), stdev = 43,730
  CI (99.9%): [333,832, 399,347] (assumes normal distribution)


# JMH version: 1.33
# VM version: JDK 16.0.1, OpenJDK 64-Bit Server VM, 16.0.1+9
# VM invoker: /Users/jonck/.sdkman/candidates/java/16.0.1.hs-adpt/bin/java
# VM options: -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63663:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8
# Blackhole mode: full + dont-inline hint (default, use -Djmh.blackhole.autoDetect=true to auto-detect)
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: benchmarking.NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis

# Run progress: 66,67% complete, ETA 00:08:25
# Fork: 1 of 5
# Warmup Iteration   1: 305,490 ops/s
# Warmup Iteration   2: 326,676 ops/s
# Warmup Iteration   3: 324,567 ops/s
# Warmup Iteration   4: 326,100 ops/s
# Warmup Iteration   5: 321,174 ops/s
Iteration   1: 326,640 ops/s
Iteration   2: 325,994 ops/s
Iteration   3: 325,028 ops/s
Iteration   4: 324,210 ops/s
Iteration   5: 321,928 ops/s

# Run progress: 73,33% complete, ETA 00:06:44
# Fork: 2 of 5
# Warmup Iteration   1: 349,454 ops/s
# Warmup Iteration   2: 366,185 ops/s
# Warmup Iteration   3: 369,984 ops/s
# Warmup Iteration   4: 367,728 ops/s
# Warmup Iteration   5: 366,373 ops/s
Iteration   1: 367,283 ops/s
Iteration   2: 370,358 ops/s
Iteration   3: 371,871 ops/s
Iteration   4: 365,912 ops/s
Iteration   5: 363,841 ops/s

# Run progress: 80,00% complete, ETA 00:05:03
# Fork: 3 of 5
# Warmup Iteration   1: 321,223 ops/s
# Warmup Iteration   2: 336,172 ops/s
# Warmup Iteration   3: 336,384 ops/s
# Warmup Iteration   4: 335,286 ops/s
# Warmup Iteration   5: 334,909 ops/s
Iteration   1: 335,553 ops/s
Iteration   2: 338,116 ops/s
Iteration   3: 334,554 ops/s
Iteration   4: 336,892 ops/s
Iteration   5: 335,340 ops/s

# Run progress: 86,67% complete, ETA 00:03:22
# Fork: 4 of 5
# Warmup Iteration   1: 371,526 ops/s
# Warmup Iteration   2: 382,950 ops/s
# Warmup Iteration   3: 384,475 ops/s
# Warmup Iteration   4: 382,519 ops/s
# Warmup Iteration   5: 352,527 ops/s
Iteration   1: 383,702 ops/s
Iteration   2: 385,614 ops/s
Iteration   3: 383,961 ops/s
Iteration   4: 368,989 ops/s
Iteration   5: 375,484 ops/s

# Run progress: 93,33% complete, ETA 00:01:41
# Fork: 5 of 5
# Warmup Iteration   1: 325,752 ops/s
# Warmup Iteration   2: 348,958 ops/s
# Warmup Iteration   3: 349,368 ops/s
# Warmup Iteration   4: 344,951 ops/s
# Warmup Iteration   5: 353,017 ops/s
Iteration   1: 347,371 ops/s
Iteration   2: 346,963 ops/s
Iteration   3: 345,520 ops/s
Iteration   4: 344,402 ops/s
Iteration   5: 348,118 ops/s


Result "benchmarking.NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis":
  350,946 ±(99.9%) 15,613 ops/s [Average]
  (min, avg, max) = (321,928, 350,946, 385,614), stdev = 20,843
  CI (99.9%): [335,333, 366,559] (assumes normal distribution)


# Run complete. Total time: 00:25:17

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                                        Mode  Cnt    Score    Error  Units
NetworkAnalysisBenchmark.benchmarkHeadRecursiveNetworkAnalysis  thrpt   25  335,693 ± 28,191  ops/s
NetworkAnalysisBenchmark.benchmarkIterativeNetworkAnalysis      thrpt   25  366,589 ± 32,757  ops/s
NetworkAnalysisBenchmark.benchmarkTailRecursiveNetworkAnalysis  thrpt   25  350,946 ± 15,613  ops/s

Process finished with exit code 0
```
