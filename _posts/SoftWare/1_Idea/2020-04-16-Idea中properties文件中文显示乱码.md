---
title: Idea中properties文件中文显示乱码
date: 2020-04-16 03:33:00
tags: 
- SoftWare
category: 
- SoftWare
description: Idea中properties文件中文显示乱码
---



**前言**     

 Github：[https://github.com/HealerJean](https://github.com/HealerJean)         

 博客：[http://blog.healerjean.com](http://HealerJean.github.io)           



Java文件的话，可以直接点右下角的那个编码格式，选择自己想要的，一般都是utf-8，但是不排除你下载个老的代码，他是gbk编码的格式。      

但是在properties文件里面，这个却是不能直接点击修改的。只能如上图那样修改，而且是**一休改之后，整个项目的properties文件都变成了utf-8的编码格式啦**。所以，注意，你修改过之后，原来写的注释，估计又变成乱码了。     



**解决方案：无解**  

**如果遇到`properties`因为`gbk`或者`utf-8`导致的乱码情况，就改成对应的呗，也没事啊，下次再改回来呗，能花几秒钟呢**   



![1587033986376](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/blogImages/1587033986376.png)







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
		id: '3fQyjaGqgNY5u8Fs',
    });
    gitalk.render('gitalk-container');
</script> 

