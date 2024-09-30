rm -rf task1-bash
mkdir task1-bash
cp jurassicParkCast.txt task1-bash/cast.txt
ls task1-bash
echo '---------------------------------------------------'
tail -n +2 task1-bash/cast.txt | sort -t, -k4
echo '---------------------------------------------------'
grep "^L" task1-bash/cast.txt
echo '---------------------------------------------------'
awk -F, '$4 ~ /F/' task1-bash/cast.txt | tee task1-bash/women.txt
echo '---------------------------------------------------'
awk -F, '$4 ~ /M/' task1-bash/cast.txt | tee task1-bash/men.txt
echo '---------------------------------------------------'
for file in task1-bash/*.txt; do
  cp "$file" "${file%.txt}-1.txt"
  cp "$file" "${file%.txt}-2.txt"
done
wc task1-bash/*