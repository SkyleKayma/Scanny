![Scanny](app/src/main/res/mipmap-xxxhdpi/ic_launcher.png)

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/SkyleKayma/Scanny/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/SkyleKayma/Scanny/tree/main) 
[![License](https://img.shields.io/badge/License-Apache-blue.svg)](https://github.com/SkyleKayma/Scanny/edit/main/LICENSE)

# Scanny

Modern app to scan and create QRCode, with history.
It does use only latest libraries and do NOT depends on Zxing.
It aims to be reliable and fast.

<a href='https://play.google.com/store/apps/details?id=fr.skyle.scanny&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img width="200" alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>

# What does it uses ?

Written in full Kotlin.

Most important libraries:
- AndroidX
- Google ML Kit
- Custom QRCode generation (https://github.com/g0dkar/qrcode-kotlin)
- Compose
- Coroutines
- Hilt
- Camera 2

# Features

What app currently features:

- [x] Scan QRCode with Camera
- [x] Find QRCode in image
- [x] Feedback system

This application can scan all types of code that Google MLKit handles.


# What will be added in the next releases:

## Next release:

- [ ] Scan history

## Upcoming releases:

- [ ] Create QRCode
- [ ] Creation history
- [ ] Customization of created QRCode
- [ ] Google In-App rating
- [ ] Google In-App update

# Contributions

If you've found an error in this project, please file an issue.

Patches are encouraged and may be submitted by forking this project and submitting a pull request. Since this project is still in its very
early stages, if your change is substantial, please raise an issue first to discuss it.

# License

```
Copyright 2020 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
