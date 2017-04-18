# MovieApp

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1b0f2ae1793d47e090bea0b6b65c2cf7)](https://www.codacy.com/app/prakh25/MovieApp?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=prakh25/MovieApp&amp;utm_campaign=Badge_Grade)
![minSdkVersion](https://img.shields.io/badge/minSdkVersion-15-yellow.svg?style=true)
![compileSdkVersion](https://img.shields.io/badge/compileSdkVersion-25-green.svg?style=true)

A simple movie browsing app using [TMDB API](https://developers.themoviedb.org/3/getting-started) as a service which contains all the information about its vast movie data library.
 _Data provided by The Movie DB._
 
 To run **locally** have your api keys in your **gradle.properties** file (project's root folder).

* MyTmdbapikey = < TMDB_API_KEY >
* MyTraktClientId = < TRAKT_CLIENT_ID >
* YouTubeApiKey = < GOOGLE_DATA_API_KEY > (To play movie trailers)

_Mind the markedown spaces._

## Screenshots

![Movie Home](../master/art/movie_home.png)
![Movie Detail](../master/art/movie_detail.png)
![Movie Images](../master/art/all_images.png)

![Movie Full Images](../master/art/full_image.png)
![People Home](../master/art/people_home.png)
![People Detail](../master/art/people_detail.png)

![App Search](../master/art/app_search.png)
![Error Layout](../master/art/error_layout.png)

## TODO

- [x] Add Message Layout
- [ ] Complete Home TV Fragment.
- [ ] Add TV Detail Activity
- [ ] Add Movie Collections Activity
- [ ] Add User Account Activity

## Dependencies
* [Design Support Library](http://developer.android.com/intl/tools/support-library/features.html#design)
The Design package provides APIs to support adding material design components and patterns to your apps.A flexible view for providing a limited window into a large data set.
* [Retrofit](http://square.github.io/retrofit)
A type-safe HTTP client for Android and Java
* [Gson](https://github.com/google/gson)
A Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object.
* [LeakCanary](https://github.com/square/leakcanary)
A memory leak detection library for Android and Java.
* [Glide](https://github.com/bumptech/glide)
A fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
* [OkHttp](https://github.com/square/okhttp)
An HTTP & HTTP/2 client for Android and Java applications.
* [Butter Knife](https://github.com/JakeWharton/butterknife)
Field and method binding for Android views which uses annotation processing to generate boilerplate code.
* [Retrolambda](https://github.com/orfjackal/retrolambda)
Backport of Java 8's lambda expressions to Java 7, 6 and 5.
* [Realm](http://realm.io)
Realm is a mobile database: a replacement for SQLite & ORMs.

### License

	Copyright © 2017 Prakhar Gupta

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
	implied.
	See the License for the specific language governing permissions and
	limitations under the License.
