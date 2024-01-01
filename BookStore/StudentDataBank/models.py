from django.db import models

# Create your models here.

class Book(models.Model):
    name = models.CharField(max_length=255,primary_key=True)
    author = models.CharField(max_length=100)
    price = models.DecimalField(max_digits=10, decimal_places=2)
    number_of_pages = models.IntegerField()
    language = models.CharField(max_length=50)
    genre = models.CharField(max_length=100)
    description = models.TextField()
    published = models.DateField()
    cover_image = models.ImageField(upload_to='book_covers/', null=True, blank=True)
    def __str__(self):
        return self.name