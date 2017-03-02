# tpi_mob_android

Simple Android app for TPI data collection  

##[Contents](#contents)
- [Architecture](#arch)
- [Plugins](#plugins)
- [Dependencies](#depends)
- [Testing](#test)
- [Entities](#entities)

##Architecture

+ Activity: 2 activities - MainActivity (crud functions) & StartActivity (login/register)
+ Fragment: 
+ Presenter: 
  + Subscriber/rx.Observer
+ Interactor
  + rx.Observable
+ Adapter
  * VenueAdapter extends RecyclerView.Adapter<BaseViewHolder> : handles the
    - VenueViewHolder extends (BaseViewHolder extends RecyclerView.ViewHolder { + itemView} )
      + bind(VenueVO)
    - @Override onCreateViewHolder -> return new VenueViewHolder
    - @Override onBindViewHolder -> bind(VenueVO) 
  * LoginAdapter
+ Entities
  * Dto: data objects
    - VenueDto
  * VO: view objects
    - VenueVO


##Plugins

+ retrolambda: it allows you to use some java7 and java8 features like lambdas, try-catch with resources, static functions in interfaces. Lambdas is the most useful feature. If an interface contains only ONE method, you can create anonymous class of it in a very short way.

##Dependencies

+ nucleus
It's an adapter for Nucleus Rx subscribers. A little confusing to use because it provides an error and a successful result in the same entity. 

So the  error should be delivered to Subscriber#onError() callback and successful result should be passed into Subscriber#onNext()

Nucleus is a good MVP framework that guarantees that all Rx events are delivered when a view is being shown(not in background).

Also Rx events survive orientation changes when delivering with Nucleus. That's very useful. That means you can start a request, rotate a screen several times and the request result will be delivered when the screen is in foreground state, no matter how many times you rotated the screen.

+ [butterknife](http://jakewharton.github.io/butterknife/javadoc/): Field and method binding for Android views. Use this class to simplify finding views and attaching listeners by binding them with annotations. 

+ [dagger]

+ [rx](http://reactivex.io/RxJava/javadoc/rx/package-summary.html): A library that enables subscribing to and composing asynchronous events and callbacks.

##[Testing](#test){#test}

+ [roboelectric](roboelectric.org): Robolectric is a unit test framework that de-fangs the Android SDK jar so you can test-drive the development of your Android app. Tests run inside the JVM on your workstation in seconds.

+ [mockito](https://static.javadoc.io/org.mockito/mockito-core/2.7.5/org/mockito/Mockito.html) is used to mock objects to test Presenters & Views. The use of a MVP architecture allows the Presenters, Views and Interactors to be tested independent of UI elements.  


##Entities

Names which end with "VO" are View Objects and their intention is only to hold minimal data that should be displayed and this data should be prepared to be shown easily (without additional complex business logic). It's made so as to make adapters more scalable, i.e. if an api interface changes, you will only need to change converters from DTO to VO

##Lifecycle

onAttach
onCreate
onCreateView
onViewCreated

##TODO
+ The presenter is injected into Fragment using dagger but the Interactor is conventionally included in the Presenter. Maybe inject that as well ?
+ 
