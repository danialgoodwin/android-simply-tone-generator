# Simply Tone Generator #

Play a tone, any tone. This app is basically two apps in one. One side is a simple DTMF tone generator, and the other side is produce-any-tone-you-want signal generator. You can hear what any frequency sounds like. The help screen in-app also provides the official frequencies for DTMF tones. When playing back recorded DTMF numbers, put your Android phone near the analog telephone's speaker so that you can dial through your PSTN (Public switched telephone network) phone.






## Changelog ##

### 1.7 (Active development) ###
- Integration with contacts

### 1.6 (2015-02-09) ###
- New Feature: Ability to record unlimited tones!
- New Feature: Ability to create titles for saved tones!
- New Feature: Ability to edit saved records
- Enhancement: Backspace button is now easier to see



## Code structure ##

- net.simplyadvanced
  - simplytonegenerator: Code specially for this app.
    - mainpage: This holds files related more specifically for the features contained to a limited areas of the app.
      - dtmf: Most of the code that is for using the features on this tab in-app.
      - dtmfrecords: Most of the code that is for using the features on this tab in-app, including database+model for saving the records.
      - tone: Most of the code that is for using the features on this tab in-app. This page definitely needs work to be made more modular, and I'm not sure if it will stay in, or it might stay perpetual "beta".
    - settings: Code dealing with user preferences and the page for altering those settings. App settings should all mostly be accessible through `UserPrefs` in this package so that implementation details are hidden from other parts of the app.
    - ui: Things related to views/UI that aren't widgets that could be used in XML layouts.
    - widget: Reuseable widgets across app(s).
  - util: Static helper methods to make working with Android easier. These are useful in any Android project, so that's why they aren't in the simplytonegenerator package.

AOSP (Android Open Source Project) code style is preferred: [https://source.android.com/source/code-style.html](https://source.android.com/source/code-style.html)
