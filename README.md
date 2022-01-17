## (Tail) recursion in the JVM
This project discusses head recursion vs tail recursion. It demonstrates why in some situations due to the nature of
the data structure, a recursive solution is much preferred over an iterative one. Next it shows how a regular head
recursive function will eventually produce a StackOverflowError.

After that we move to how the Kotlin compiler applies the tail-call optimization, and we take a look at the 
difference on a bytecode level when we compare the exact same code except for one of the functions having the 
tail-call optimization applied and the other does not.

And to conclude we will discuss how we can also use tail-recursion in Java.

To see the bytecode of the Kotlin class NetworkAnalysis.class, move to the target directory containing that class
file and execute the following:
```
javap -c NetworkAnalysis.class
```

We can then study the differences between the two functions:

```
public final java.lang.Long calculateSizeTailRecursion(io.vavr.collection.Array<network.Node>, long);
Code:
0: aload_1
1: ldc           #41                 // String nodes
3: invokestatic  #22                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
6: aload_0
7: astore        4
9: aload_1
10: astore        5
12: lload_2
13: lstore        6
15: aload         4
17: astore        8
19: aload         5
21: astore        9
23: lload         6
25: lstore        10
27: aload         9
29: invokevirtual #45                 // Method io/vavr/collection/Array.isEmpty:()Z
32: ifeq          43
35: lload         10
37: invokestatic  #51                 // Method java/lang/Long.valueOf:(J)Ljava/lang/Long;
40: goto          116
43: aload         9
45: invokevirtual #55                 // Method io/vavr/collection/Array.head:()Ljava/lang/Object;
48: checkcast     #57                 // class network/Node
51: astore        12
53: aload         8
55: astore        4
57: aload         9
59: invokevirtual #61                 // Method io/vavr/collection/Array.tail:()Lio/vavr/collection/Array;
62: aload         12
64: invokevirtual #64                 // Method network/Node.getChildren:()Lio/vavr/collection/Array;
67: checkcast     #66                 // class java/lang/Iterable
70: invokevirtual #70                 // Method io/vavr/collection/Array.appendAll:(Ljava/lang/Iterable;)Lio/vavr/collection/Array;
73: astore        13
75: aload         13
77: ldc           #72                 // String nodes\n                    .tail()\n                    .appendAll(head.children)
79: invokestatic  #33                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
82: aload         13
84: astore        5
86: lload         10
88: aload         12
90: invokevirtual #76                 // Method network/Node.getSize:()Ljava/lang/Long;
93: astore        13
95: aload         13
97: ldc           #78                 // String head.size
99: invokestatic  #33                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
102: aload         13
104: checkcast     #80                 // class java/lang/Number
107: invokevirtual #84                 // Method java/lang/Number.longValue:()J
110: ladd
111: lstore        6
113: goto          15
116: areturn
```

```
public final java.lang.Long calculateSizeHeadRecursion(io.vavr.collection.Array<network.Node>, long);
Code:
0: aload_1
1: ldc           #41                 // String nodes
3: invokestatic  #22                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
6: aload_1
7: invokevirtual #45                 // Method io/vavr/collection/Array.isEmpty:()Z
10: ifeq          20
13: lload_2
14: invokestatic  #51                 // Method java/lang/Long.valueOf:(J)Ljava/lang/Long;
17: goto          83
20: aload_1
21: invokevirtual #55                 // Method io/vavr/collection/Array.head:()Ljava/lang/Object;
24: checkcast     #57                 // class network/Node
27: astore        4
29: aload_0
30: aload_1
31: invokevirtual #61                 // Method io/vavr/collection/Array.tail:()Lio/vavr/collection/Array;
34: aload         4
36: invokevirtual #64                 // Method network/Node.getChildren:()Lio/vavr/collection/Array;
39: checkcast     #66                 // class java/lang/Iterable
42: invokevirtual #70                 // Method io/vavr/collection/Array.appendAll:(Ljava/lang/Iterable;)Lio/vavr/collection/Array;
45: astore        5
47: aload         5
49: ldc           #72                 // String nodes\n                    .tail()\n                    .appendAll(head.children)
51: invokestatic  #33                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
54: aload         5
56: lload_2
57: aload         4
59: invokevirtual #76                 // Method network/Node.getSize:()Ljava/lang/Long;
62: astore        5
64: aload         5
66: ldc           #78                 // String head.size
68: invokestatic  #33                 // Method kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
71: aload         5
73: checkcast     #80                 // class java/lang/Number
76: invokevirtual #84                 // Method java/lang/Number.longValue:()J
79: ladd
80: invokevirtual #90                 // Method calculateSizeHeadRecursion:(Lio/vavr/collection/Array;J)Ljava/lang/Long;
83: areturn
```
