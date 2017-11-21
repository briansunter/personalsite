+++
uuid = "why-clojure"
tags = ["blog", "index-page"]
title = "Why Clojure?"
description = """Why would someone want to use a different programming language than the one they're already using? Why not node.js?
"""
+++

# Why Clojure?
Pure functions and immutable data are the easiest units of software to reason about. 

- Most "features" from other languages can be added as extensions via macros or in terms of the language itself. Polymorphism, Types, inheritance, Pattern Matching, etc.
- Interactive Programming: extremely fast feedback loop and experimentation. 
-  Good interop with the worlds most popular languages: Java and Javascript. 
- Excellent concurrency support: Immutability, Software Transactional Memory, "Go Channels" (CSP). 

# (but (there (are (so (many (parens (🙀)))))))

```
(->> but
there
are
so
many
parens)
```

We can always rewrite syntax repetition with macros and there are plenty of techniques for reducing the number of parens including "threading" operators like `->>`. 

# I must have types
I initially disliked clojure coming from my semi strongly typed Java and C++. How was I supposed to `⌘+R` to refactor all instances of `foo` when I add a field in my IDE. I should have been asking why I needed to change 23 files. If you use types you have to consider the limits of what they do and the cost of the coupling introduced by type information flowing through your program.  

# Win Without Fighting
Part of using clojure is a win without fighting approach. If you can write your programs using pure functions and dealing with data on a need to know basis, you can write dramatically simpler code which lessens your need for a type system. When everything deals with data the tests are also much simpler and easier to write. I like taking the extra time I save fighting with a type and investing it in tests, which I feel do more for correctness. 









