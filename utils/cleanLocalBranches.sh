echo "checking out develop"
git checkout develop
echo "*** LOCAL branches to delete ***"
git branch --merged develop | grep -v develop$
while true; do
    read -p "Are these the LOCAL branches you would like to delete? (y/n)" yn
    case $yn in
        [Yy]* ) git branch --merged develop | sed 's/ *origin\///' | grep -v 'develop$' | xargs git branch -d; break;;
        [Nn]* ) exit;;
        * ) echo "Please answer Y/y or N/n.";;
    esac
done
echo "Done deleting merged LOCAL branches!"