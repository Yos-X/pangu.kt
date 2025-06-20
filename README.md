Pangu.kt
==========

[![](https://img.shields.io/badge/made%20with-%e2%9d%a4-ff69b4.svg?style=flat-square)](https://vinta.ws/code/)

智能文本排版工具，自动在中文、日文、韩文和半角英文、数字、符号之间插入适当的空格

Paranoid text spacing for good readability, to automatically insert whitespace between CJK (Chinese, Japanese, Korean), half-width English, digit and symbol characters.

- [pangu.go](https://github.com/vinta/pangu) (Go)
- [pangu.java](https://github.com/vinta/pangu.java) (Java)
- [pangu.js](https://github.com/vinta/pangu.js) (JavaScript)
- [pangu.py](https://github.com/vinta/pangu.py) (Python)
- [pangu.space](https://github.com/vinta/pangu.space) (Web API)

## 此版本的注意事项 / Notes for this version
由于采用了窄空格进行替换 (U+202F)，在某些环境下可能无法正常显示，请多加注意

This version uses narrow non-breaking spaces (U+202F), which may not display correctly in some environments. Please use with caution.

## 下载 / Download

请先设置 JitPack repository / Set JitPack repository first

Maven:
```xml
<dependency>
    <groupId>com.github.Yos-X</groupId>
    <artifactId>pangu.kt</artifactId>
    <version>1.0.0</version>
</dependency>
```

or Gradle:

```groovy
dependencies {
    implementation 'com.github.Yos-X:pangu.kt:1.0.0'
}
```

## 使用方法 / Usage

```Kotlin
import ws.vinta.pangu.Pangu

fun main() {
    val pangu = Pangu()
    val newText = pangu.spacingText("小华为了考试提前就醒来了，挤了5g牙膏刷刷牙就匆匆赶到学校了")
    println(newText)
    // 输出 Output: "小华为了考试提前就醒来了，挤了 5g 牙膏刷刷牙就匆匆赶到学校了"
}
```