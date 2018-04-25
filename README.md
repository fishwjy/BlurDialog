# BlurDialog
A blur dialog in Android which is high-imitation of iOS.
<div  align="center">    
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/confirm.png" 
       width = "270" height = "480" alt="确认对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/single.png" 
       width = "270" height = "480" alt="单选项对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/delete.png" 
       width = "270" height = "480" alt="删除对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/none.png" 
       width = "270" height = "480" alt="无选项对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/list.png" 
       width = "270" height = "480" alt="列表对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/waiting.png" 
       width = "270" height = "480" alt="等待对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/progress.png" 
       width = "270" height = "480" alt="进度对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/cus_confirm.png" 
       width = "270" height = "480" alt="自定义确认对话框" align=center />
  <img src="https://github.com/fishwjy/BlurDialog/raw/master/art/cus_list.png" 
       width = "270" height = "480" alt="自定义列表对话框" align=center />
</div>

## Step
### 1.Import to your project
    compile 'com.vincent.filepicker:MultiTypeFilePicker:latestVersion'
    
### 2.Add Render Script API to your project
```gradle
android {
    ...
    defaultConfig {
        ...
        renderscriptTargetApi 19
        renderscriptSupportModeEnabled true    // Enable RS support
        ...
    }
    ...
}
```

### 3.Use the dialog as the sample code

## Thanks
Inspired by [RealtimeBlurView](https://github.com/mmin18/RealtimeBlurView)

## License
```
Copyright 2018 Vincent Woo

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
