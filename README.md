# task_06_recyclerview
Contact app with  RecyclerView

Приложение с контактами, отображаемыми в RecyclerView.
Контакты генерируются процедурно путем выбора случайных имени и фамилии, выбираемых из массивов лежащих в array.xml
Номер получаю через Random.nextInt(), картинки берерутся с https://picsum.photos/, ссылка на картинку генерируются случайная.
В случае если нет возможности загрузить картинку, то на userPic устанавливается изображение-заглушка placeholder_error .

Контакты генерируются в количестве 121 штуки. Я попытался имитировать ViewModel, для чего создал полем в MainActivity класс DataModel 
и все данные по контактам получаю оттуда.

При клике по вьюхолдеру открывается фрагмент с деталями контакта, при лонгклике - Dialog с удалением контакта.
Возможность изменить данные контакта не реализована.
Реализван поиск по фамилии и имени.
Попытался в DiffUtils, но он получился медленным и не всегда корректно отображает данные, поэтому я передаелал его на проcтой 
notifyDatasetChange.

