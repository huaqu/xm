# 项目介绍
本项目基于webmagic，优雅的实现了一个爬虫。

## 经过本项目你将学习到

* webmagice 爬虫框架的基本用法，以及如何搭建一个java爬虫
* 如何将设计模式的基本原则贯彻到项目开发中
* 策略模式、装饰器模式、观察者模式的具体使用
* 如何利用设计模式将一个已有的框架进行优雅的扩展。

当然，你还能够学到很多其他的东西，但是我们认为以上四点是本项目的核心知识。

## 你需要准备哪些知识

* 掌握java基本语法
* 理解多态、继承（你可以去看《java编程思想》中的相关章节）
* 了解设计模式，理解策略模式、装饰器模式、观察者模式
* 了解爬虫的实现思路
* 了解Html、DOM、http协议
* 了解Spring Boot

**如果你还不了解上述的这些知识，去看书复习一下吧，花不了多久！**

## 步骤：

* [step-1-project-framework: 搭建项目框架](https://git.nowcoder.com/11000160/spider/tree/step-1-project-framework)
* [step-2-framework-extension: 策略模式对原框架做拓展](https://git.nowcoder.com/11000160/spider/tree/step-2-framework-extension)
* [step-3-onebook-implement: 完成单一页面的爬取](https://git.nowcoder.com/11000160/spider/tree/step-3-onebook-implement)
* [step-4-books-iterator: 装饰器模式扩展](https://git.nowcoder.com/11000160/spider/tree/step-4-books-iterator)
* [step-5-pipeline-extension: 使用观察者模式对Pipeline做扩展](https://git.nowcoder.com/11000160/spider/tree/step-5-pipeline-extension)

## 那我们开始吧！

# 第一步：搭建项目框架

> * 1. 将项目clone到本地（最开始是空项目），根据sql搭建起自己的数据库。
    
    git clone https://git.nowcoder.com/11000160/spider.git
> * 2. 开始进入项目开发的第一步。

    git checkout step-1-project-framework