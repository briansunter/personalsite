+++
uuid = "config-languages"
tags = ["blog", "index-page"]
title = "JSON, YAML, TOML and EDN"
description = "Are there better alternatives for configuration languages like JSON and YAML?"
+++

# TOML
>Are there better alternatives for configuration languages like JSON and YAML?

# What I Want
I want to be able to look at a configuration file and see that it's correct without a lot of syntax noise.

# YAML and JSON
The two most popular options are YAML and JSON. YAML is nice because it's readable and is very flexible. However it is extremely complex and whitespace errors can cause subtle bugs. The complexity of YAML makes parsers slow and difficult to write. JSON doesn't have the whitespace issues but the nesting and quotes create a lot of noise, which can also create subtle bugs.

# Tom's obvious mark-up language
TOML is a simpler YAML that is not based on whitespace. It is still fairly new but is used in the Rust language's package manager, Cargo.
As the name suggests, the syntax is intuitive. All the constructs map well to JSON and common data structures.

This creates a key value map.

`title = "my title"`

You do need to quote strings but at least they give you a multi-line strings.

``` toml
description="""this is extremely useful
For longer fields"""
```

# How I use TOML
I wrote a task that parses TOML meta-data at the top of the markdown entries. This is the metadata of this page. Perun, my static site generator, uses this metadata to display and arrange the posts. The site is mostly based around the concept of "tags", where tagging an element determines which page it's on.

``` ini
+++
uuid = "why-toml"
tags = ["blog", "index-page"]
title = "Why TOML?"
description = "Why TOML? What about YAML? JSON?"
+++
```

One of my favorite features is called a "table array", which allows you to specify an array of maps without heavy nesting. It is a bit verbose but I think it's quite readable and flexible.

``` ini
[[images]]
title= "Icy Roofed Houses"
caption= "ICY!"
image= "DSC00020.jpg"

[[images]]
title= "Glacier"
image= "DSC00028.jpg"
```

This turns into

```
{"images":
  [{"title": "Icy Roofed Houses",...},
   {"title":"Glacier",...}]}
```

You can specify nested data structures with a readable syntax without relying on the ambiguity of whitespace.

# EDN
EDN is also very interesting. It has the same syntax as Clojure data structures, which are nested but have a very succinct syntax. EDN is much more powerful and complex than other markup languages. It allows you to define new types such as Set, Date, and float, instead of relying on just the javascript built-in types like number.

```
; #{1 2 3} is a hash set of values

{:foo #{1 2 3}}
```

# All the formats

## JSON
``` json
{
  "fruit": [
    {
      "name": "apple",
      "physical": {
        "color": "red",
        "shape": "round"
      },
      "variety": [
        { "name": "red delicious" },
        { "name": "granny smith" }
      ]
    },
    {
      "name": "banana",
      "variety": [
        { "name": "plantain" }
      ]
    }
  ]
}

```
## YAML
``` yaml
fruit:
- name: apple
  physical:
    color: red
    shape: round
  variety:
  - name: red delicious
  - name: granny smith
- name: banana
  variety:
  - name: plantain
```

## TOML
``` ini
[[fruit]]
name = "apple"

[fruit.physical]
color = "red"
shape = "round"

[[fruit.variety]]
name = "red delicious"

[[fruit.variety]]
name = "granny smith"

[[fruit]]
name = "banana"

[[fruit.variety]]
name = "plantain"

```
## EDN
```
{:fruit
 [{:name "apple",
   :physical {:color "red", :shape "round"},
   :variety
   [{:name "red delicious"}
    {:name "granny smith"}]}
  {:name "banana",
   :variety [{:name "plantain"}]}]}
```
