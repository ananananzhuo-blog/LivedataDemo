> 关注我的公众号 **“安安安安卓”** 免费学知识
关注公众号学习更多知识


![](https://files.mdnice.com/user/15648/404c2ab2-9a89-40cf-ba1c-02df017a4ae8.jpg)

很大一部分文字内容描述是直接翻译官网的

## Livedata 概览

LiveData 是一种可观察的数据存储器类。与常规的可观察类不同，LiveData 具有生命周期感知能力

如果观察者（由 Observer 类表示）的生命周期处于 STARTED 或 RESUMED 状态，则 LiveData 会认为该观察者处于活跃状态。。LiveData 只会将更新通知给活跃的观察者。为观察 LiveData 对象而注册的非活跃观察者不会收到更改通知。

您可以注册与实现 LifecycleOwner 接口的对象配对的观察者。有了这种关系，当相应的 Lifecycle 对象的状态变为 DESTROYED 时，便可移除此观察者。这对于 Activity 和 Fragment 特别有用，因为它们可以放心地观察 LiveData 对象，而不必担心泄露

## LiveData 优势

1. 数据符合页面状态
2. 不会发生内存泄露
3. 不会因 activity 停止而导致崩溃
4. 不再需要手动处理生命周期
5. 数据始终保持最新状态
6. 可以用来做资源共享

## Livedata 使用

一般来说我们会在 ViewModel 中创建 Livedata 对象，然后再 Activity/Fragment 的 onCreate 中注册 Livedata 监听（因为在 onStart 和 onResume 中进行监听可能会有冗余调用）

### Livedata 简单使用

仍然还是用我们倒计时的例子，在 Viewmodel 中开始一个 2000s 的倒计时，然后通过 Livedata 回调给 Activity 进行更新界面，代码：

1. viewmodel 代码

```
class CountDownModel : ViewModel() {

    val countDownLivedata = MutableLiveData<String>()
    private var remainSecond = 2000//剩余秒数

    init {
        val countDown = object : CountDownTimer(2000 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainSecond--
                countDownLivedata.postValue("剩余：${remainSecond} 秒")
            }

            override fun onFinish() {
                countDownLivedata.postValue("倒计时结束")
            }
        }
        countDown.start()
    }
}
```

2. activity 中观察数据更新 ui 代码

```
 val countDownModel: CountDownModel by viewModels<CountDownModel> {
        ViewModelProvider.NewInstanceFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        countDownModel.countDownLivedata.observe(this, object : Observer<String> {
            override fun onChanged(value: String?) {
                value?.let {
                    tv_countdown_remainsecond.text = it
                }
            }
        })
    }
```

3. 效果图

![](https://files.mdnice.com/user/15648/c4998410-2488-4dfc-a653-0688c8c49a67.gif)

### 使用全局 Livedata 在多个视图监听状态

本例实现的 demo 效果是，创建一个全局的倒计时，然后在 Activity 中添加两个按钮，点击后可以切换 FragmentA 和 FragmentB。然后我们通过全局的自定义 LiveData 单例实现数据监听，切换 Fragment 后 Fragment 页面上会展示倒计时的剩余秒数

代码：

1. 全局自定义 Livedata 代码

```
class GlobalLivedata : LiveData<String>() {
    val coundManager = CountDownManager()
    val listener = object : OnDataChangeListener {
        override fun change(data: String) {
           postValue(data)
        }
    }
    override fun onActive() {
        super.onActive()
        coundManager.setListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        coundManager.removeListener(listener)
    }
    companion object {
        private lateinit var globalData: GlobalLivedata
        fun getInstance(): GlobalLivedata {
            globalData = if (::globalData.isInitialized) globalData else GlobalLivedata()
            return globalData
        }
    }
}
```

2. 倒计时器代码较长只粘贴一部分，有兴趣可以到 github 去查看完整代码

```
 private val listeners = mutableListOf<OnDataChangeListener>()

    init {
        val countDown = object : CountDownTimer(2000 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainSecond--
                callback("剩余：${remainSecond} 秒")
            }

            override fun onFinish() {
                callback("倒计时结束")
            }
        }
        countDown.start()
    }

    /**
     * 循环遍历回调消息
     */
    private fun callback(msg:String) {
        for (listener in listeners){
            listener.change(msg)
        }
    }
```

3. FragmentA、FragmentB 中监听倒计时状态

```
GlobalLivedata.getInstance().observe(viewLifecycleOwner,
            { t ->
                inflate.findViewById<TextView>(R.id.tv_fragmentA).text = "fragmenta：${t}"
            })
```

```
 GlobalLivedata.getInstance().observe(viewLifecycleOwner,
            { t ->
                inflate.findViewById<TextView>(R.id.tv_fragmentB).text = "fragmentb：${t}"
            })
```

4. 最终效果

![](https://files.mdnice.com/user/15648/075cfa77-73e2-4901-9c10-2acf35891d45.gif)

最终效果，当我们切换 Fragment 的时候两个 Fragment 显示的秒数是一致的，其实即使我们马上启动一个新 activity 去查看剩余秒数也是一样的，有兴趣的朋友可以下载 git 代码自己尝试

### 对 Livedata 进行转换

map 和 switchMap 两个方法可以对已有的 Livedata 进行转换得到新的 Livedata

#### Transformation.map

在 activity 中观察 viewmodel 中的数据更新，当点击 activity 中按钮的时候会调用 viewmodel.sendData 方法发送数据，然后发送的数据会做一定的转换给 activity，然后 activity 打印日志展示

直接看代码吧：

1. 创建 viewmodel，model 中创建 Livedata

```
class TransMapViewModel: ViewModel() {
    fun sendData() {
        userLivedata.value=User("李白",1200)//对userLivedata进行复制
    }
    val userLivedata =MutableLiveData<User>()
    val mapLiveData = Transformations.map(userLivedata){
        "${it.name} : ${it.age}"//这里可以返回任意类型的数据
    }
}
data class User(var name:String,var age:Int)

```

代码中 mapLiveData 是对 userLivedata 进行转换得到的，所以当我们调用 sendData 方法更新 userLivedata 中的方法时，mapLiveData 的回调也会触发

2. 在 activity 中观察 mapLiveData 并点击按钮发送小数据

```
 mapViewModel.mapLiveData.observe(this,{
            logEE(it)
            tv_map.text=it
        })
        btn_map.setOnClickListener {
            mapViewModel.sendData()
        }
```

#### Transformation.switchMap

本例中我们实现如下逻辑：

在 activity 中观察 viewmodel 中的数据更新，当点击 activity 中按钮的时候会调用 viewmodel.sendData 方法发送数据，然后发送的数据会做一定的转换给 activity，然后 activity 打印日志展示

1. viewmodel 中代码

```
class SwitchMapViewModel : ViewModel() {
    fun sendData() {
        userLivedata.value = SwitchUser("李白", 1200)
    }
    private val userLivedata = MutableLiveData<SwitchUser>()
    val mapLiveData = Transformations.switchMap(userLivedata) {
        changeUser(it!!)
    }
    private fun changeUser(it: SwitchUser): LiveData<String> {
        return MutableLiveData("${it.name}  的名字杜甫知道")
    }
}
data class SwitchUser(var name: String, var age: Int)
```

2. 调用部分代码

```
model.mapLiveData.observe(this, {
            logEE(it)
        })
        btn_switchmap.setOnClickListener {
            model.sendData()
        }
```

#### 合并两个 Livedata（MediatorLiveData）

想象这样一个场景，您的 app 里面有一个评论列表的功能，可以对列表内容进行点赞。每一个点赞都是一个异步任误，你的产品需求并不想让用户点太多赞，比如一分钟点赞数量不能超过 10 次，这种场景就很适合用 Livedata 的合并功能

我们就不模拟这么复杂的场景了，我们的例子做这样一个事情：

界面上有两个按钮，点一次相当于点赞一次，我们点击十次按钮就在界面上展示文字提示用户已经点击了十次数据。

代码展示：

1.model 代码

```
class MeditorLiveViewModel : ViewModel() {

    var count =0//计数字段
    fun setData1(name: String) {
        liveData1.value = name
    }
    fun setData2(age: Int) {
        liveData2.value = age
    }
    private val liveData1 = MutableLiveData<String>()
    private val liveData2 = MutableLiveData<Int>()
    val liveCombind = MediatorLiveData<String>()
    init {
        liveCombind.addSource(liveData1) {
            increase()
        }
        liveCombind.addSource(liveData2) {
           increase()
        }
    }
    private fun increase() {
        count++
        if(count==10){
            liveCombind.value="安安安安卓同学，您已经点击 ${count}次，再点我也不跟你玩了，收手吧。。。"
        }
    }
}
```

model 中创建了三个 Livedata，其中两个分别是 livedata1 和 livedata2，分别对应其中两个按钮。

还有一个 liveCombind 用来回调超过十次调用的场景

init 方法中 liveCombind.addSource 调用就是表示用来中间拦截 livedata1 和 livedata2 的数据更新，处理 count 累加和是否回调 liveCombind 的功能

2. activity 中代码

```
  model.liveCombind.observe(this){
            logEE(it)
            tv_count.text=it
        }
        btn_livedata1.setOnClickListener {
            model.setData1("李白")
        }
        btn_livedata2.setOnClickListener {
            model.setData2(1000)
        }
```

3. 实现效果

![](https://files.mdnice.com/user/15648/9fdd859e-5ca0-4b85-931a-8a8747a845c5.gif)
####  observeForever
 observeForever方法也是注册Livedata监听的方法，表示即使应页面被覆盖处于不活跃状态也可以收到数据改变的回调

#### Livedata和协程联合使用


##### emit方式使用
1. 引入依赖
有时候你可能需要处理异步任务，任务处理完成后刷新ui

这种情况可以使用Livedata的扩展程序实现

本例我们实现下面的逻辑：

在viewmodel中阻塞4s，然后通知activity

代码：

1. 引入依赖插件
```
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'
```
2. 开启异步任务方法

```
 /**
     * 开启异步任务
     */
    fun startAsyncWithSecond(second: Int): LiveData<String> = liveData<String> {
        delay(second * 1000L)
        emit("倒计时结束")//用来触发数据回调
    }
```
当我们调用startAsyncWithSecond方法的时候会马上返回一个Livedata对象，供我们注册监听
3. activity中注册livedata监听

```
  model.startAsyncWithSecond(3).observe(this){
                logEE(it)//model中delay  3s后会返回数据到这里
            }
```
4. 效果展示
![](https://files.mdnice.com/user/15648/eff12fcf-dcce-40fd-b81d-753ffd9468ed.gif)

##### emitSource使用
使用emitSource的效果等同于MediatorLiveData的效果

我们本例实现如下的效果：

点击按钮开启一个3s的异步任务，然后通知activity打印日志。

然后再次开启一个3s的异步任务，结束后再次通知activity打印日志


代码：


1. 创建异步任务方法

```
 fun startAsyncEmitSource(second: Int)= liveData<String> {
        delay(second * 1000L)
        emit("${second} 秒阻塞完成,再阻塞三秒后通知你")
        val emitSourceLivedata = MutableLiveData<String>()
        emitSource(
            emitSourceLivedata
        )
        delay(second*1000L)
        emitSourceLivedata.value="再次阻塞${second}秒完成"
    }
```
2. activity中注册监听


```
  model.startAsyncEmitSource(3).observe(this){
                logEE(it)
            }
```
3. 效果

![](https://files.mdnice.com/user/15648/8110d763-a114-4541-bc21-ac463c4f1f50.gif)






