echo "checking out develop"
git checkout develop
echo "*** REMOTE branches to delete ***"
git branch -r --merged develop | grep -v develop$
while true; do
    read -p "Are these the REMOTE branches you would like to delete? (y/n)" yn
    case $yn in
        [Yy]* ) git branch -r --merged develop | sed 's/ *origin\///' | grep -v 'develop$' | xargs -i% git push origin :%; break;;
        [Nn]* ) exit;;
        * ) echo "Please answer Y/y or N/n.";;
    esac
done
echo "Done deleting merged REMOTE branches!"