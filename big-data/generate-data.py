from faker import Faker
import pandas as pd
import numpy as np
from datetime import datetime, timedelta
import random

# Initialize Faker
fake = Faker()

# Define data generation parameters
num_customers = 100000
num_products = 50
num_transactions = 300000
start_date = datetime(2023, 1, 1)
end_date = datetime(2023, 12, 31)

# Generate Customers Data
customers_data = [{
    'customer_id': i,
    'name': fake.name(),
    'age': random.randint(18, 70),
    'gender': random.choice(['M', 'F']),
    'country': fake.country()
} for i in range(num_customers)]

# Generate Products Data
categories = ['Electronics', 'Clothing', 'Home & Garden', 'Books', 'Toys & Games']
products_data = [{
    'product_id': i,
    'product_name': fake.catch_phrase(),
    'category': random.choice(categories),
    'list_price': round(random.uniform(10, 1000), 2)
} for i in range(num_products)]

# Generate Transactions Data
transactions_data = []
for i in range(num_transactions):
    transaction_date = start_date + (end_date - start_date) * random.random()
    product_id = random.randint(0, num_products - 1)
    customer_id = random.randint(0, num_customers - 1)
    quantity = random.randint(1, 10)
    list_price = [p['list_price'] for p in products_data if p['product_id'] == product_id][0]
    discount = random.uniform(0, 0.3)  # Discount up to 30%
    price_paid = round(list_price * (1 - discount), 2)
    
    transactions_data.append({
        'transaction_id': i,
        'product_id': product_id,
        'customer_id': customer_id,
        'quantity': quantity,
        'price_paid': price_paid,
        'transaction_date': transaction_date.strftime('%Y-%m-%d')
    })

# Convert to Pandas DataFrame
df_customers = pd.DataFrame(customers_data)
df_products = pd.DataFrame(products_data)
df_transactions = pd.DataFrame(transactions_data)

# Display sample data
df_customers.head(), df_products.head(), df_transactions.head()

# Save to CSV in the accessible directory
customers_file_path = 'customers_revised.csv'
products_file_path = 'products_revised.csv'
transactions_file_path = 'transactions_revised.csv'

# Save to CSV
df_customers.to_csv(customers_file_path, index=False)
df_products.to_csv(products_file_path, index=False)
df_transactions.to_csv(transactions_file_path, index=False)

(customers_file_path, products_file_path, transactions_file_path)
