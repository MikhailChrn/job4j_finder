# job4j_finder

## О проекте

Консольная утилита для поиска файлов.

Утилита ищет данные в заданном каталоге и подкаталогах.

Имя файла может задаваться: целиком, по маске, по регулярному выражению(не обязательно).

Утилита должна запускаться с параметрами, например:  -d=c:  -n=*.?xt -t=mask -o=log.txt

Ключи

	-d - директория, в которой начинать поиск.
 
	-n - имя файла, маска, либо регулярное выражение.
 
	-t - тип поиска: mask искать по маске, name по полному совпадение имени, regex по регулярному выражению.
 
	-o - результат записать в файл.

Параметры в утилиту должны передаваться в командной строке.

Утилита записывает результаты в файл.

Утилита реализует валидацию ключей и даёт подсказку.