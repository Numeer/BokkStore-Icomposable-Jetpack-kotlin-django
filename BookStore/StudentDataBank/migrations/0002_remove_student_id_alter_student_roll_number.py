# Generated by Django 4.2.7 on 2023-11-20 11:13

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('StudentDataBank', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='student',
            name='id',
        ),
        migrations.AlterField(
            model_name='student',
            name='roll_number',
            field=models.CharField(max_length=10, primary_key=True, serialize=False),
        ),
    ]
