# SSA with LLVM

## 介绍

1. 这是**软件分析与测试**课程的第二次作业，由学号MG1933019完成。
2. 本次作业采用LLVM对C程序进行分析，构造完全无法转换成SSA的例子，提交源代码和转换代码。
3. LLVM使用的版本为**Windows LLVM 9.0.0**，下载地址：http://releases.llvm.org/download.html

## 构造代码

构造思路：如果一个变量被取址过(&)，那么这个变量将不会被转换成SSA

```c
// 文件名：test.c
int goSSA(){
	// 构造能完全转换的代码
	int a = 0;
	a = a + 1;
	
	// 构造不可完全转换的代码
	int b = 2;
	int *p = &b; // 取址操作
	*(p+1) = 5;
	*p = 3;
	return 0;
}
```

## LLVM转换SSA - (1)

首先根据一篇[CSDN博客1](https://blog.csdn.net/qq_29674357/article/details/78731713)根据对LLVM IR进行学习，主要有这个坑需要注意：LLVM IR为了编译效率，默认采用的 -O0优化，生成的IR会利用**alloca**将栈中的变量转换到内存中，从而这些变量不需要符合SSA的形式（即使代码能转换为完全SSA）

   例如根据命令:

   ```C
   clang -S -emit-llvm test.c -o -
   ```

   对test.c进行转换得到输出:

   ```C
   ; ModuleID = 'test.c'
   source_filename = "test.c"
   target datalayout = "e-m:w-i64:64-f80:128-n8:16:32:64-S128"
   target triple = "x86_64-pc-windows-msvc19.11.0"
   
   ; Function Attrs: noinline nounwind optnone uwtable
   define dso_local i32 @goSSA() #0 {
     %1 = alloca i32, align 4
     %2 = alloca i32, align 4
     %3 = alloca i32*, align 8
     store i32 0, i32* %1, align 4
     %4 = load i32, i32* %1, align 4
     %5 = add nsw i32 %4, 1
     store i32 %5, i32* %1, align 4
     store i32 2, i32* %2, align 4
     store i32* %2, i32** %3, align 8
     %6 = load i32*, i32** %3, align 8
     %7 = getelementptr inbounds i32, i32* %6, i64 1
     store i32 5, i32* %7, align 4
     %8 = load i32*, i32** %3, align 8
     store i32 3, i32* %8, align 4
     ret i32 0
   }
   ```

## LLVM转换SSA - (2)

这个形式显然是有问题的，为避免这种特性，获得比较纯粹的SSA转换形式，于是采用O1的编译方式，给Clang加上优化。

```c
clang -S -emit-llvm test.c -o -O1
```

转换结果

```c
; ModuleID = 'test.c'
source_filename = "test.c"
target datalayout = "e-m:w-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-windows-msvc19.11.0"

; Function Attrs: norecurse nounwind readnone uwtable
define dso_local i32 @goSSA() local_unnamed_addr #0 {
  ret i32 0
}
```

值得注意的是，goSSA()函数内部只剩下了'ret i32 0'，其余部分均被省略。

## LLVM转换SSA - (3)

目前还是没能得到完整性较好的SSA转换结果，参考了[博客2](https://wiki.aalto.fi/display/t1065450/LLVM+SSA)。其中谈到，利用**opt  -mem2reg**这一命令将内存中的引用转换到寄存器中。

运行命令

```bash
clang -S -emit-llvm ./example1.c -o test.ll
opt -mem2reg -S ssa.ll -o -
```

得到的输出为

```C
; ModuleID = 'test.c'
source_filename = "test.c"
target datalayout = "e-m:w-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-windows-msvc19.11.0"

; Function Attrs: nounwind uwtable
define i32 @goSSA() #0 {
  %b = alloca i32, align 4
  %1 = add nsw i32 0, 1
  store i32 2, i32* %b, align 4
  %2 = getelementptr inbounds i32* %b, i64 1
  store i32 5, i32* %2, align 4
  store i32 3, i32* %b, align 4
  ret i32 0
}
```

## 总结与引用

- LLVM的安装过程中还发现直接安装二进制文件，可能会缺失opt工具包的问题
- [1] https://blog.csdn.net/qq_29674357/article/details/78731713
- [2] https://wiki.aalto.fi/display/t1065450/LLVM+SSA

