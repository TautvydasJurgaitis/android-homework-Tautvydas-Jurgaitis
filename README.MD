## Welcome to Vinted!

We, engineers at Vinted, are striving to provide the best solutions for business-side problems which scale, last and require minimal maintenance. Our code is clean, well-tested and flexible and we are not afraid to touch legacy code, re-evaluate it and rework it if necessary. We hope that our new colleagues appreciate the same values and stick to best-practices.

In this homework you won't need to write application completely from scratch. As a starting point, we provide you already written app with some initial functionality. Feel free to express your opinions not only about certain narrow scope parts of initial code but also about overall architecture, applied principles, technology stack. You can express your opinions as comments in the pull requests. It is not necessary, but in the implementation part feel free to change the initial code according to your needs, use other industry-standard libraries if you see how it could contribute to a better, more scalable overall solution.

You may find that initial code contains usages of some not anymore trending technologies, like Kotlin synthetics for views or RxJava. We want to assure you that initial code with mentioned technologies doesn't represent whole Vinted app codebase. We quite actively adopt state-of-the-art android ecosystem technologies. For example, we are moving from RxJava to other asynchronous programming solution for Kotlin. However, old technologies are more or less a reality of high-scale project. If you will join Vinted - you will have to deal with some of them.

With all of that in mind, we have prepared 3 tasks for you.

### Instructions

In the provided repository you will find an open PR (not really, more info below), which is a working Vinted app prototype code left by the previously working developer.

It has two basic screens - an item feed screen where the user is able to scroll and see the items. Clicking one of the items the user is taken to another screen with item details (with future capability of buying it).

Due to technical limitations of github classrooms you need to perform such git commands after checking out the repo (to refresh git history):
* git checkout origin/feature/feed -b feature/feed
* git rebase main
* git push -f

Then open a PR from the branch feature/feed to the main branch.

Let’s imagine that you have arrived at the Vinted office for the first day of work.

Since you are a new developer working on these screens, your first task is to understand the code. And as the previous developer did not do a hand-over of the code, you should re-evaluate the existing code.

1. *Please write a PR review of new classes in the feature/feed -> main PR (and anything general you notice with the rest of the code), like you would do for a regular PR.*

Just before you jump into code you are approached by your new and eager to deliver Product Manager who asks you to implement your first feature - the search of items

2. *The user should have an option to input the search phrase by which the provided items in the list are filtered. The most feasible approach would be that results are filtered while typing the search phrase. Open this as a separate PR to the feature/feed branch.*

It is mid-summer, holiday time, and the designer is hiking in some far away country, the PM of your team trusts your superior design skills and gives you freedom to implement it how you see fit (we know you could make it look amazing, but please don’t over-design this though).

You might want to get a new API to provide the filtered values, but your backender loves to reuse the existing APIs - she whispers that you can just add a new key-value pair to the only one API you use (items). The key is ”search_text” and the value - the string you want to use for filtering.
 
Just before you get your third cup of coffee, a stressed analytics guy comes over with some bad news - one of the most vital and precious analytics events (all the events must be tracked) - tracking the shown items in the feed - is not implemented yet!

3. *Track each item the user has seen while scrolling the item feed - upload the details of the event to a POST endpoint “http://mobile-homework-api.vinted.net/impressions”, accepting a list of `ItemSeenEvent(timestamp: Long, item_id: Int)`. For simplicity’s sake, let’s consider the item seen when the item’s image is loaded. Open this as a separate PR to the feature/feed branch.*

After he leaves (chanting “no data - no life” to himself as he walks away), you look around - no one else is coming with some unexpected and/or urgent task (disclaimer - it is highly unlikely for the provided situation to be this chaotic in reality. But chaos is a ladder...). You sit down finally having a chance to write some code.

When you're done, just ping your Vinted contact!
