# Practice_AndroidDesignPatterns
随书练习：《Android源码设计模式-解析与实战》

`SOLID`原则：

- 单一职责(`SRP - Single Responsibility Principle`)
    + 一个类只做一件事
- 开闭原则(`OCP - Open Close Principle`)
    + 对修改封闭、对扩展开放
- 里氏替换原则(`LSP - Liskov Subsitution Principle`)
    + 第一种定义：如果对每一个类型为S的对象O1，所有类型为T的对象O2，使得以T定义的所有程序P在所有的对象O1都代换成O2时，程序P的行为没有发生变化，那么类型S是类型T的子类型。
    + 第二种定义：所有引用基类的地方必须能透明的使用其子类对象
    + 通俗点讲，只要父类能出现的地方，子类就可以出现，而且替换为子类也不会产生任何错误或异常，使用者可能根本就不需要知道是父类还是子类。但是反过来就不行了，有子类出现的地方，父类未必就能使用。
- 依赖倒置(`DIP - Dependence Inversion Principle`)
    + 面向接口编程(例子：IImageCache)
- 接口隔离(`ISP - InterfaceSegregation Principles`)
    + 客户端不应该依赖它不需要的接口(`Closeable`)

迪米特原则`LOD - Law of Demeter`:`一个对象应该对其他对象有最少的了解`。（类与类之间的关系越密切，耦合度越大）例子：租户、中介、房屋。


