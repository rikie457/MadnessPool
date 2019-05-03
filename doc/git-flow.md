# Git flow using GitLab

```plantuml
|Alice|

start
:Assigns an issue to herself;

:Creates a merge request;

repeat

    :Checks out the merge branch in Android Studio
    using `git fetch` and `git branch`;
    
    repeat
        :Edits code;
        
        :Commits and pushes;
        
    repeat while (Issue requires more work)
     
    :Removes WIP status from merge request;
    
    :Starts working on something else;
    
|Bob|
    
    :Offers to do the review;
    
    :Inspects the changes
     (possibly opening the branch in Android Studio);
    
    :Gives feedback in the merge request on GitLab;
    
    
repeat while (Not good enough)

:Gives his approval to the merge request;

|Alice|
:Does the merge;

end

```