+++
uuid = "why-clojure"
tags = ["blog", "index-page"]
title = "Why Clojure?"
description = """Why would someone want to use a different programming language than the one they're already using? Why not node.js?"""
+++

<link rel="stylesheet" type="text/css" href="https://storage.googleapis.com/app.klipse.tech/css/codemirror.css" />

# Why Clojure?
Why not the programming language I'm already using or some other language?

- Pure functions and immutable data are the easiest units of software to reason about
- Deep support for immutable data structures
- S-Expressions (parens) are re-usable and composable
- Great support for destructuring and pattern matching, and working with maps in general
- Most "features" from other languages can be added as extensions via macros or in terms of the language itself. Polymorphism, "types", inheritance, pattern matching, etc
- Interactive Programming: extremely fast feedback loop and experimentation with the REPL
- Powerful and simple testing due to emphasis on pure functions
- Good interop with the worlds most popular languages: Java and Javascript
- Excellent concurrency support: immutability, software transactional memory, "Go Channels" (CSP), agents, everything in the JVM/Java
- Subjectively good design - Strong notions of things like time, identity, and equality

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
The first thing you will notice in Clojure is how many parens there are and how dense the code is. It takes some getting used to, but the parens have a lot of benefits.

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

``` clj
(if (= 42 42) "True" "False")
(my-if (= 42 42) "True" "False")
```
The parens replace a lot of the curly brace notation in other languages.

``` js
function myFunction(arg1, arg2) {
  if (arg1) {
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
Go has support for asynchronous "go channels" due to special syntax baked into the language. Clojure added the same features and syntax as a third party library. In Javascript you have to wait for syntax to be adopted but in Clojure it could by implemented by anyone.

```
messages := make(chan string)
go func() { messages <- "ping" }()
msg := <-messages
fmt.Println(msg)
```

```
(def c (chan 10)
(go (>! c "hello"))
(println (<!! c))
```

# Maps and Destructuring
Clojure is really good at extracting data from maps and sequences. It is a really good for "data programs", that are mostly calling an api, transforming a sequence, and calling another API.

## Positional Destructuring
```
(def large-list '(1 2 3 4 5 6 7 8 9 10))
(let [[a b c] large-list]
  (println a b c))
;= 1 2 3
```
## Destructuring with named optional parameters and defaults

```
(defn configure [val options]
(let [{:keys [debug verbose]
       :or {debug false, verbose false}} options]
(println "val =" val " debug =" debug " verbose =" verbose)))
```

# Interactive Programming
Having a fast feedback loop is crucial to be productive. When I first started programming I would write some code, compiles, then manually test my changes, maybe with a debugger. Then I discovered TDD with an auto test runner, which gave me a faster feedback loop, since I could be reasonably confident my program worked without having to recompile for every change. The fastest feedback loop I've discovered so far is the Clojure REPL with editor integration. With Emacs and CIDER I can execute code in my editor as I write it. Having a fast feedback loop for exploratory coding before writing tests helps me be a lot more productive and write higher quality code. Other languages also have REPLs but I feel Clojure is uniquely well suited to this workflow.

# Testing
Testing is simpler when most things are maps and pure functions. This is an example from the "Gilded Rose Kata".

```
    @Test public void
        BackstagePassQualityControl qualityControl = new BackstagePassQualityControl();

    shouldNeverIncreaseQualityToMoreThanFifty() {
Item backstagePass = anItem()
                .withName(BACKSTAGE_PASS_ITEM_NAME)
                .build()
        backstagePass.setSellIn(FIVE_DAYS);
        backstagePass.setQuality(50);

        qualityControl.updateQualityFor(backstagePass);

        assertThat(backstagePass.getQuality(), is(50));
    }
```

``` clj
(def max-quality-pass
  {:quality 50
   :sell-in 5})

 (deftest test-backstage-pass-peak
  (testing "Quality never goes over 50"
    (is (= 50 (:quality (i/update-item max-quality-pass))))))
```

# Java and Javascript Interop
Clojure has good interop with the worlds most popular languages. You can tap into the Java ecosystem for foundational libraries like the AWS SDK or database clients. Clojurescript has an excellent wrapper around React called Reagent. You can write your entire stack in Clojure, meaning a single person can be extremely productive. The interop story isn't perfect though: although it works technically, the difference between the programming models does have some friction. This can usually be solved by writing a wrapper.

`(System/getProperty "java.vm.version")`

# Concurrency
Now that Moore's Law is ending, we can't rely on speed increases of a single core anymore. We need to write code that can take advantage of multiple cores and that can correctly run in parallel. I don't feel good about using some languages like Python or Javascript that are single inherently single threaded. Languages like Java or C++, which weren't designed with concurrency in mind are hard to use correctly. Clojure's data structures are thread safe by default and it has numerous concurrency primitives. The language design de-emphasis the us of state and emphasizes the use of values instead.

# I must have types
I initially disliked clojure coming from my semi strongly typed Java and C++. How was I supposed to `⌘+R` to refactor all instances of `foo` when I add a field in my IDE. I should have been asking why I needed to change 23 files. If you use types you have to consider their downsides and the cost of the coupling introduced by type information flowing through your program. After using Clojure, I find things like the "builder pattern" contrived. There is usually only a few types of true "data" and the rest of the program are subsets and combinations of the data, which don't always deserve an explicit name or type. I feel using classes encourages you to make abstractions too early, and making the wrong abstraction is much worse than repetition. A lot of the "bugs" you catch at compile time are often self inflicted bookeeping mistakes due to the increased complexity.

# Good Design
Clojure wasn't designed
