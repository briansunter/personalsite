+++
uuid = "rxjs-patterns"
tags = ["blog"]
title = "rxjs Patterns"
description = """
Some interesting problems you can solve with reactive programming
"""
+++

# rxjs Patterns
<script src="https://unpkg.com/@reactivex/rxjs@latest/dist/global/Rx.min.js"></script>


```eval-js
[1,2,3].map(e => e + 1)
```

# rxjs is based on observables
```eval-js

Rx.Observable.of(`Hello World`)
.subscribe(result => console.log(result));

```

```eval-js
var source = Rx.Observable.timer(200, 100)
    .timeInterval()
    .pluck('interval')
    .take(3);

var subscription = source.subscribe(
    function (x) {
        console.log('Next: ' + x);
    },
    function (err) {
        console.log('Error: ' + err);
    },
    function () {
        console.log('Completed');
    });
```
