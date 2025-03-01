切换至国内高速[NPM镜像](https://so.csdn.net/so/search?q=NPM镜像&spm=1001.2101.3001.7020)源是一个不错的选择。使用淘宝NPM镜像

```bash
npm config set registry https://registry.npmmirror.com
```

增加命令的详细日志输出级别，可以更好地了解安装过程中哪个环节出现问题：

```bash
npm install --verbose
```

本地npm缓存的问题也可能导致安装过程停滞不前。可以先清理缓存再重试安装：

```bash
npm cache clean --force
npm install
```

升级到最新版npm也是解决此类问题的一个有效途径，因为新版npm可能会优化网络请求和依赖处理机制：

```bash
npm install -g npm
```



1.1 读取 package.json 文件
读取项目根目录下的 package.json 文件，确认当前项目所依赖的模块及其版本。

1.2 解析依赖关系
基于依赖的版本信息解析出需要安装的具体版本，并处理依赖之间的版本冲突等问题，最终生成一个依赖树。

                 **注意如果有lock文件，会按照lock文件来

1.3. 下载模块
   根据解析的依赖树，npm 会从注册源头载所需的模块包。

                ** 如果本地缓存有，会读取本机的缓存

1.4. 安装模块
下载的模块会解压到 node_modules 目录下，并会处理依赖树中每个模块的依赖，确保每个模块都有其所需的依赖项。
1.5. 生成或更新 package-lock.json
安装完成后，npm 会更新或生成 package-lock.json 文件，以确保以后可以确定性地安装相同版本的依赖。
1.6. 执行生命周期钩子
有些包会在安装过程中触发钩子（如 preinstall, postinstall），执行额外的任务。

