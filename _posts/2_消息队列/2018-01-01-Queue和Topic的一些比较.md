---
title: Queue和Topic的一些比较
date: 2018-01-01 03:33:00
tags: 
- MQ
category: 
- MQ
description: Queue和Topic的一些比较
---

#### 

**前言**     

 Github：[https://github.com/HealerJean](https://github.com/HealerJean)         

 博客：[http://blog.healerjean.com](http://HealerJean.github.io)    





### 1、下载地址

[http://activemq.apache.org/download-archives.html](http://activemq.apache.org/download-archives.html)



JMS中定义了两种消息模型

+ **点对点（point to point， queue）:不可重复消费，默认是持久化的**
+ **发布/订阅（publish/subscribe，topic） : 重复消费，默认是非持久的（要求消费者必须在线）**



这里重复消费的意思是，可以多次读取，也就是同一个消息被多个消费者读取。而不是消息签收  



### 2、Queue

```
消息生产者生产消息发送到queue中，然后消息消费者从queue中取出并且消费消息，消息被消费以后，queue中不再有存储，所以消息消费者不可能消费到已经被消费的消息。

Queue支持存在多个消费者，但是对一个消息而言，只会有一个消费者可以消费、其它的则不能消费此消息了。当消费者不存在时，消息会一直保存，直到有消费消费
```



### 3、Topic

```java
消息生产者（发布）将消息发布到topic中，同时有多个消息消费者（订阅）消费该消息。和点对点方式不同，发布到topic的消息会被所有订阅者消费。

非持久化：当生产者发布消息，不管是否有消费者。都不会保存消息
持久化：会保存消息
```





![ContactAuthor](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/assets/img/artical_bottom.jpg)





<link rel="stylesheet" href="https://unpkg.com/gitalk/dist/gitalk.css">

<script src="https://unpkg.com/gitalk@latest/dist/gitalk.min.js"></script> 
<div id="gitalk-container"></div>    
 <script type="text/javascript">
    var gitalk = new Gitalk({
		clientID: `1d164cd85549874d0e3a`,
		clientSecret: `527c3d223d1e6608953e835b547061037d140355`,
		repo: `HealerJean.github.io`,
		owner: 'HealerJean',
		admin: ['HealerJean'],
		id: 'GQVYeXfJcWAiau7b',
    });
    gitalk.render('gitalk-container');
</script> 

