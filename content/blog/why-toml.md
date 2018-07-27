+++
uuid = "why-toml"
tags = ["blog", "index-page"]
title = "Why TOML?"
description = "Why TOML? What about YAML? JSON?"
+++

# TOML

Why TOML? What about YAML? JSON?

# What I want in a configuration language
I want to be able to look at a configuration file and see that it's correct and not have to `{"nest": {"every": {"damn": {"thing": "!"}}}}`

# YAML and JSON
The two most popular options are YAML and JSON. YAMl is nice because it's readable and is very flexible. However it is extremely complex and whitespace errors can cause subtle bugs. JSON doesn't have the whitespace issues but the  nesting and quotes create a lot of noise, which can also create subtle bugs.

# Tom's obvious markup language
TOML is a simpler YAML that is not based on whitespace. It is still fairly new but is used in the Rust language's package manager, Cargo.

As the name suggests, the syntax is intuitive. All the constructs map well to JSON and common data structures.

This creates a key value map.

`title = "my title"`

You do need to quote strings but at least they give you a multiline strings.

``` toml
description="""this is extremely useful
For longer fields"""
```

# How I use TOML
I wrote a boot task that parses TOML metadata at the top of entries. I heavily rely on metadata for generating my photo albums. I use metadata for generating my photo albums. I need this for build time processing of image dimensions.
One of my favorite features is called a "table array", which allows you to specify an array of maps.

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
{"images": [{"title": "Icy Roofed Houses", ...}
{"Glacier",...}]}
```
