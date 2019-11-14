+++
uuid = "dotfiles"
tags = ["blog", "index-page"]
title = "Dotfiles"
description = "How I manage my dotfiles and bootstrap a new machine"
+++

# Dotfiles
>How I manage my dotfiles and bootstrap a new machine

# What are dotfiles?
There are a number of important programs like your terminal, emacs, and vim which are configured by files starting with a dot (`.zshrc`) in your home directory.

# How to manage dotfiles
Instead of editing your dotfiles in your home directory, I recommend creating a git repo and symlinking the files into your home directory. I create a `.dotfiles` folder in my home directory and use GNU `stow` to symlink the files in my home directory.

# GNU `stow`
GNU stow is strictly a symlink manager. It has a folder structure convention that allows you to create symlinks in the folder above it. If I run `stow` it will create symlinks in the directory above where `stow` was run.

```
/Users/bsunter/.dotfiles
├── .gitmodules
├── Brewfile
├── README.md
├── emacs
│   ├── .emacs.d
│   └── .spacemacs
├── git
│   └── .gitconfig
├── iterm
│   └── ItermProfile.json
├── macos
│   └── bootstrap.sh
├── manage.sh
├── vim
│   └── .vimrc
└── zsh
    ├── .stow-local-ignore
    ├── .zshrc
    └── lib
```
