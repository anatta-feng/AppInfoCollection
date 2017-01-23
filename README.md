# AppInfoCollection
一款监视前台应用，性能状况，并且记录以便导入 Excel 中形成可视化数据
> 目前仅支持 4.4 及以下，今天看看能搞到 5.0 以上不

**注1： 5.0 以上需要将 apk push 至 `/system/priv-app/` 目录下，然后重启手机就可以正常使用了。**

**注2： 记录文件存储路径为 `/data/data/com.fxc.appinfo/files/appInfo.txt`**

**注3： 拿出记录文件后直接用 Excel 打开即可看到表格，选中数据插入图表即可实现可视化数据**

**注4： 如果 adb push 的时候提示 `Read-only file system` 输入 `adb remount` 然后再重新 push 即可**

**注5： 记得给 app 悬浮窗权限**

