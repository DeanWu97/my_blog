branch_number = `echo $1 | awk -F'-' '{print $2}'`
git checkout -b 'cscc_'+$bramch_number 
