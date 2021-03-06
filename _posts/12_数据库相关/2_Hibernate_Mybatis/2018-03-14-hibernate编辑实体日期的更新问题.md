---
title: hibernate编辑实体日期的更新问题
date: 2018-03-14 16:33:00
tags: 
- Database
category: 
- Database
description: hibernate编辑实体日期的修改问题
---
**前言**     

 Github：[https://github.com/HealerJean](https://github.com/HealerJean)         

 博客：[http://blog.healerjean.com](http://HealerJean.github.io)            



对于刚刚接触hibernate的开发者来说，它确实是一个伟大的东西，但如果针对修改不太明白的话，那确实挺伤的。首先修改是根据id查找到实体。然后和保存一样，也是用save进行存储。

针对一些cdate创建日期，udate更新日期。我们希望创建日期只在添加是实体的时候创建，但是修改实体的时候cdate不发生改变，但是udate是在每次更新实体的时候改变，包括添加实体的时候。  



好了废话，不多说。开始吧！！！   

再多说一句，下文分析两种开发情况展开的，一种是维护，一种是从头开发。   

从头开始我这里的意思是根据hibernate实体类进行映射到数据库，然后自动生成表。   

而我这里的维护是指，不能通过hibernate实体对象来创建表，只能通过sql填写。这两种所用到的注解可能稍微有点不一样的哦

## 1、从头开始开发，hibernate映射数据表

这个就很简单的了，很明显用到的注解就是，<font color="red">二者注解并没有对日期的默认值产生影响,说明是java的解决方式</font>


```java
@UpdateTimestamp //自动更新
@Temporal(TemporalType.TIMESTAMP)
private Date udate;

@Temporal(TemporalType.TIMESTAMP)
@CreationTimestamp //自动创建
private Date cdate;


1、所映射到数据库中的字段为，注意这里映射的为datetime，其实和自己sql创建的timestamp是一样的。
2、该注解并没对日期的默认值产生影响，说明是java层的操作

  `cdate` datetime DEFAULT NULL,
  `udate` datetime DEFAULT NULL,


```
有了这两个注解，那么在添加数据的时候日期都会自动生成。

<font color="red" >正常情况下的更新，其实都是将id查找出来</font>，然后通过前台传来的值赋值给它，然后才能更新。这种情况用上面的注解完全没有任何问题。

但是还有一种情况就是更新和添加用到同一个方法，而且传到后台的参数也是一样的（<font color="red" > **除了udae更新和cdate创建日期，我这里因为前段变成了时间戳，没有必要传到后台**</font>）。而且更新的时候将id也传了过来。我们这个时候不通过id查询实体(因为查找数据库浪费时间)，而是直接将这些参数组装成一个实体。然后save。这个时候，问题来了。udate很正常，没有问题。但是cdate这个时候就会变成空，    



关于cdate，如下图。可以这样理解，这个注解只是观察是否是第一次创建，在插入数据的的时候数据中该字段是否有值，如果是第一次创建，那么好了，我给你插入当前的时间，如果不是第一次创建，并且传来的值是空的，那么好了，我他妈生气了，给你个空吧，所以这种情况下@CreationTimestamp的就不可以使用了。如果不是第一次创建，也就是更新操作，那么传来如果cdate字段有值，可以理解为不发生变化。就和替换了一样

<font color="red" >第一次创建添加实体，@CreationTimestamp没有问题；但是更新操作，就是原封不动放入数据。</font>
<font color="red" >@UpdateTimestamp没有问题；一直没有问题。</font>


```java
//更新操作的时候，参数中已经包含了id
@Override
public void savePictureCardBag(ZbtPictureCardBag pictureCardBag) {
    pictureCardBag.setStatus(ZbtPictureCardBag.STATUS_ENABLE); //可用状态
    pictureCardBagDAO.save(pictureCardBag);


}
```
![WX20180314-155103@2x](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/blogImages/WX20180314-155103@2x.png)


## 2、维护开发，使用sql进行创建数据表。

这个时候，还是一样的道理，如果我们是通过id查询到实体，更新的话，用上面的二者没有任何问题

解决方法，来了，哈哈。就是sql解决

1、不使用上面的注解，甚至下面的注解也没必要，下面的注解虽然可以生成实体字段，但是我们是自己写的原生sql呀，只是为了介绍下udate和cdate到底是什么类型的，让程序员看清楚。

下面这两个，很明显，都提供了默认值，但是udate在更新的时候会自动刷新，。但是save的时候给了cdate值，那当然，万一给了值（有时候难免），他就变了呀，岂不就和我们本意不一样了呀。而且哪有创建时候一直随着更新数据变化的。


```java
  `udate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `cdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date udate;                             //更新日期

  @Temporal(TemporalType.TIMESTAMP)
  private Date cdate;


```

2、终极解决方法，。不管数据库总写的是什么sql。因为都是按照java的方式解决的

这样无论是根据id查出来的更新，还是原封不动的更新，都ok了。哈哈哈，很牛逼吧。当然建议用id查出来更新，但是如果更新和添加用同一个方法，此时id也给过来了。那就下面这种情况非常使用了）

```java
@UpdateTimestamp //自动更新,这里注解如何使用了上面的NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 是可以去掉的,如果数据库中使用了这段sql，那么这个也是可以去掉的，
@Temporal(TemporalType.TIMESTAMP)
private Date udate;  

@Temporal(TemporalType.TIMESTAMP)
//即使数据库中已经定义了它的默认值为CURRENT_TIMESTAMP~~~~columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP"，也不可以去掉，因为hibernatesave的是时候是全部赋值的，也就说传递进入的时间为null，或者使用注解 @CreateTimeStamp
@Column(insertable = true,updatable = false)
//上面的解决方式，insertable和updatable 已经做好了万全之策
private Date cdate;


```



![ContactAuthor](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/assets/img/artical_bottom.jpg)



<!-- Gitalk 评论 start  -->

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
		id: 'UDiR5fDXoimEQEAz',
    });
    gitalk.render('gitalk-container');
</script> 

<!-- Gitalk end -->

