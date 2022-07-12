if [ $1 == '.' ]
then
	commit_id=`git log | head -n1 | awk '{print $2}'`
else
	commit_id=`git log $1 | head -n1 | awk '{print $2}'`
fi
proj_name=`pwd | awk -F'/' '{print $5}'`
url='http://git.flyudesk.com/udesk/'$proj_name'/commit/'$commit_id
open $url

