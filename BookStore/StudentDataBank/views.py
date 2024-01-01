from django.http import HttpResponse, JsonResponse
from django.core import serializers
from django.shortcuts import get_object_or_404
from .models import Book
from django.contrib.auth import authenticate

def index(request):
    return HttpResponse("Welcome to Mobile App Development with Numeer :)")

def login(request):
    
        if request.method == 'GET':
            username = request.GET.get('username')
            password = request.GET.get('password')
            
            user = authenticate(username=username, password=password)
            
            if user is not None:
                 return HttpResponse("Login successful", status =200)
            else:
                return HttpResponse("Invalid credentials", status=401)
        else:
            return HttpResponse("Method not allowed", status=405)
    
def all_books(request):
    try:
        books = Book.objects.all()
        data = serializers.serialize('json', books)
        return HttpResponse(data)
    except Exception as e:
        return HttpResponse(f"An error occurred: {str(e)}", status=500)

def del_book(request):
    try:
        name = request.GET.get('name')
        if name:
            book = Book.objects.filter(name=name)
            if book.exists():
                book.delete()
                return HttpResponse(f"Record of {name} is deleted successfully")
            else:
                return HttpResponse(f"No record found with the name {name}", status=404)
        else:
            return HttpResponse("Name parameter is missing", status=400)
    except Exception as e:
        return HttpResponse(f"An error occurred: {str(e)}", status=500)

def add_book(request):
    try:
        if request.method == 'GET':
            name = request.GET.get('name', '')
            author = request.GET.get('author', '')
            price = float(request.GET.get('price', 0.0))
            number_of_pages = int(request.GET.get('noOfPages', 0))
            language = request.GET.get('language', '')
            genre = request.GET.get('genre', '')
            description = request.GET.get('description', '')
            published = request.GET.get('published', '')

            book = Book(
                name=name,
                author=author,
                price=price,
                number_of_pages=number_of_pages,
                language=language,
                genre=genre,
                description=description,
                published=published,
            )
            book.save()
            return HttpResponse(f"Record of {book.name} is added successfully")
        else:
            return HttpResponse("Invalid request method", status=405)
    except ValueError as ve:
        return HttpResponse(f"Invalid data format: {str(ve)}", status=400)
    except Exception as e:
        return HttpResponse(f"An error occurred: {str(e)}", status=500)

def update_book(request):
    try:
        if request.method == 'GET':
            name = request.GET.get('name', '')
            book = get_object_or_404(Book, name=name)

            book.author = request.GET.get('author', book.author)
            book.price = float(request.GET.get('price', book.price))
            book.number_of_pages = int(request.GET.get('noOfPages', book.number_of_pages))
            book.language = request.GET.get('language', book.language)
            book.genre = request.GET.get('genre', book.genre)
            book.description = request.GET.get('description', book.description)
            book.published = request.GET.get('published', book.published)
            
            book.save()
            return HttpResponse(f"Record of {book.name} is updated successfully")
        else:
            return HttpResponse("Invalid request method", status=405)
    except ValueError as ve:
        return HttpResponse(f"Invalid data format: {str(ve)}", status=400)
    except Book.DoesNotExist:
        return HttpResponse(f"No record found with the name {name}", status=404)
    except Exception as e:
        return HttpResponse(f"An error occurred: {str(e)}", status=500)
