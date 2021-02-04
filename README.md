# To-Do Application
This to-do app implements MVVM architecture using Hilt, Room, Coroutines and Navigation component.

![Application snapshot](https://github.com/mal7othify/to-do/blob/master/ezgif-3-e5dcf289fbf7.gif {width=40px height=400px}))

<img src="https://github.com/mal7othify/to-do/blob/master/ezgif-3-e5dcf289fbf7.gif" width="200">


## Included functions: 
* Show tasks with their corresponding priorities.
* Add new tasks including task title, description, and priority.
* Edit existing tasks including task title, description, and priority.
* View a specific task information.
* Delete a task using swipe gesture.
* Undo deleting a task.
* Support for material design (dark theme)

## Architecture
Model-view-viewmodel (MVVM) & Single activity architecture.

#### App packages
* **data:** contains task data accessing and manipulating components.
* **di:** Dependency providing classes using _Hilt_.
* **ui:** View classes along with their corresponding ViewModel.
* **util:** Utility class.

#### Dependecy Injection (DI)
This app uses **Hilt** which is a dependency injection library built on top of Dagger.


## Library references 
* [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/).
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel).
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata).
* [DataStore](https://developer.android.com/topic/libraries/architecture/datastore).
* [Room Persistence Library](https://developer.android.com/jetpack/androidx/releases/room).
* [Parcelable](https://developer.android.com/reference/android/os/Parcelable.html).
* [Navigation component](https://developer.android.com/guide/navigation/).
* [Kotlin coroutines](https://developer.android.com/kotlin/coroutines).
* [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview).
* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

## Contributions
You are more than welcome to contribute. 🤩

## License

```
   Copyright (C) 2021

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
