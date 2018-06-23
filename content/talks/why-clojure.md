+++
uuid = "why-clojure"
tags = ["talk", "index-page"]
title = "Why Clojure?"
description = """Why would someone want to use a different programming language than the one they're already using? Why not node.js?"""
+++

<link rel="stylesheet" type="text/css" href="https://storage.googleapis.com/app.klipse.tech/css/codemirror.css" />

# Why Clojure?

- Pure functions and immutable data are the easiest units of software to reason about
- Deep support for immutable data structures
- S-Expressions and functions are very re-usable and composable
- Great support for destructuring and pattern matching
- Most "features" from other languages can be added as extensions via macros or in terms of the language itself. Polymorphism, types, inheritance, pattern Matching, etc
- Interactive Programming: extremely fast feedback loop and experimentation
- Powerful and simple testing
- Good interop with the worlds most popular languages: Java and Javascript
- Excellent concurrency support: Immutability, Software Transactional Memory, "Go Channels" (CSP), Agents, Everything in the JVM/Java
- Subjectively Good Design - Strong notions of things like time, identity, and equality

# Pure Functions and Immutable Data Structures
Instead of mutating existing data structures, Clojure encourages you to use immutable data structures. In many languages you create a mutable array object and append to it.

``` js
var myArray = ['one', 'two', 'three'];

function addOne(item) {
  myArray.push(item);
}

myArray
```

We mutate the original array.

``` js
addOne('four');
myArray
```

In Clojure, whenever you "append" to an vector (array) you get a "new" vector and the orignal does not change. Anyone with a reference to the original can always count on it being the same.

``` clj
(def my-vector ["one" "two" "three"])
my-vector
```

``` clj
(def my-new-vector (conj my-vector "four"))
my-new-vector

```

```clj
my-vector

```
Since we can't mutate existing data, we're encouraged to use pure functions. You can count on these functions always returning the same value for a given input. Functions like `reverse` return a new vector, rather than mutating the original.

``` js
const myArray = ["one", "two", "three"];
myArray.reverse();

myArray
```

``` clj
(def my-vector ["one" "two" "three"])
(reverse my-vector)

```

Our original vector is unchanged.
``` clj
my-vector
```

This encourages us to use compositions of functions instead of functions that mutate objects. This makes a lot of things simpler and gives us confidence that changes in one part of the code won't affect areas in another. We just need to focus on the scope of the function, the inputs, and the outputs. We don't need to worry about prior state.

# (but (there (are (so (many (parens (🙀)))))))
The first thing everyone notices in Clojure is how many parens there are and how dense the code is. It takes some getting used to, but the parens have a lot of benefits.
We can always rewrite syntax repetition with macros and there are plenty of techniques for reducing the number of parens including "threading" operators like `->>`. The following is equivalent to the header.

```
(->> but
     there
     are
     so
     many
     parens)
```

Function calls are different in clojure than most languages. It is represented by a list where the first element is the function, and the rest are the arguments to that function.

``` clj

(defn my-function
  [arg1 arg2]
  (str arg1 arg2))

(my-function "first" "second")
```

The syntax is extremely regular. It's natural to wrap a function in another function.

``` clj
(my-function (my-function "first" "second") "third")
```

Since all functions including built in functions are called the same way, it's easy to swap any function out with another, including built-ins.

The parens replace a lot of the curly brace notation in other languages.

``` js
function myFunction(arg1, arg2) {
  if (arg1.length !== 0) {
    return arg1;
  } else {
    return arg2;
  }
}
```

``` clj
(defn my-function
  [arg1 arg2]
  (if (not (blank? arg1))
     arg1
     arg2))
```
# Macros
Go has support for asynchronous "go channels" due to special syntax baked into the language. Clojure added the same features and syntax as a third party library. In Javascript you have to wait for syntax to be adopted but in Clojure it could by anyone.

# I must have types
I initially disliked clojure coming from my semi strongly typed Java and C++. How was I supposed to `⌘+R` to refactor all instances of `foo` when I add a field in my IDE. I should have been asking why I needed to change 23 files. If you use types you have to consider the limits of what they do and the cost of the coupling introduced by type information flowing through your program.

# Win Without Fighting
Part of using clojure is a win without fighting approach. If you can write your programs using pure functions and dealing with data on a need to know basis, you can write dramatically simpler code which lessens your need for a type system. When everything deals with data the tests are also much simpler and easier to write. I like taking the extra time I save fighting with a type and investing it in tests, which I feel do more for correctness.
