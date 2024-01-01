
from django.urls import path

from . import views

urlpatterns = [
    path("", views.index, name="index"),
    path("login", views.login, name="login"),
    path("allBooks", views.all_books, name="allBooks"),
    path("delBook", views.del_book, name="delBook"),
    path("addBook", views. add_book, name=" addBook"),
    path("updateBook",views.update_book, name="updateBook"),
]