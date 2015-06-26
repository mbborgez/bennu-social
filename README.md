![](https://cloud.githubusercontent.com/assets/132118/5009829/70603546-6a63-11e4-96fc-9d88559fa600.png)

# Available Integrations

### Github
requested scopes as parameter on auth url redirect
authorized scopes returned in accessToken answer (may be a subset of requested scopes)

list of [valid scopes](https://developer.github.com/v3/oauth/#scopes)

> Note: see [Normalized Scopes](https://developer.github.com/v3/oauth/#normalized-scopes)

### Linkedin  

requested scopes configured in Linkedin.  [Example link (will not work if you have no developer permission for this app)](https://www.linkedin.com/developer/apps/4544731/auth).
requested scopes may be overriden on auth url redirect

### Facebook
requested scopes as parameter on auth url redirect

authorized scopes with [Inspect access tokens endpoint](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/v2.3#checktoken)

> Note: see [Facebook reference](https://developers.facebook.com/docs/facebook-login/permissions/v2.3) for more details over scopes

### Twitter
requested scopes configured in Twitter.  [Example link (will not work if you have no developer permission for this app)](https://apps.twitter.com/app/8573395/permissions).

### Flickr
request scopes as parameter on auth url redirect

list of [valid scopes](https://www.flickr.com/services/api/auth.howto.web.html)

### Dropbox
permissions defined in creation

### Bitbucket
requested scopes configured in Bitbucket.

### Google


# How to add another integration

* Create domain entities
    * socialAPI (extends api.SocialAPI)
    * socialUser (extends user.SocialUser)
* Create domain relationships
    * org.fenixedu.bennu.core.domain.User - socialUser
    * org.fenixedu.bennu.core.domain.Bennu - socialAPI
    * socialAPI - socialUser
* Set Bennu on socialAPI constructor
* Implement singleton pattern on socialAPI
    * private constructor
    * static getInstance()
    * [Atomic TxMode.WRITE] private initialize()
* Implement abstract methods on socialAPI
    * revokeAllAccesses()
    * revokePermission(User)
    * getAuthenticatedUser(User)
    * getAuthenticationUrlForUser(User)
    * getCallbackURL()
* Implement methods on socialUser
    * constructor with User
    * delete()
* Create GoogleService
* Add googleService to SocialAdminController
* Add socialAPI to SocialAdminController
    * admin
    * adminSave
* Add socialAPI to admin interface (admin.jsp)
    * <td></td> with socialAPI
    * resources for socialAPI name
    * modal for socialAPI config
* Add socialAPI to SocialUserController
* Add socialAPI to user interface (user.jsp)
    * <td></td> with socialAPI
    * modal for socialAPI revoke
* Add socialAPI to SocialCallbackController
* Add socialAPI scopes anotation
    * RequiresSocialAPI
    * SocialInitializer.onStartup()
    * socialAPI.ensureConsistentScope()
