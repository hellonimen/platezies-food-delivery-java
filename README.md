# Platezies: food ordering and delivery system (Java Swing)

A desktop food ordering and delivery system for a food court. Customers browse menus and order, vendors manage their stall, delivery runners work through delivery tasks, and a manager and an admin oversee the whole thing. It is a Java Swing application with flat-file storage, built as a group coursework project at Asia Pacific University and shared here with my teammates' permission, with names removed.

## At a glance

| | |
|---|---|
| Language | Java (needs JDK 17 or newer, the code uses switch expressions) |
| UI | Swing, designed in the NetBeans GUI Builder (`.form` files included) |
| Storage | Plain text files in `platezies_txtfiles/`, one per data type |
| Size | 49 classes, about 13,500 lines including the generated GUI code |
| Charts | JFreeChart (vendor and delivery runner performance) |
| Roles | Customer, vendor, delivery runner, manager, admin |

## What each role can do

- **Customer:** register, browse the menu, place dine-in, takeaway or delivery orders, pay or top up credit, get a receipt, follow order status and history, view transactions, rate vendors and delivery runners, and send complaints.
- **Vendor:** edit the stall menu, manage incoming orders and update their status, read customer reviews, manage notifications, see order history and monthly performance.
- **Delivery runner:** work through assigned delivery tasks (delivering and completed tabs, with batch processing), see task history, check delivery revenue and read customer reviews.
- **Manager:** see vendor and delivery runner performance charts, a revenue dashboard, the vendor listing, and customer feedback.
- **Admin:** manage customer, vendor and runner profiles and approve credit top-up requests.

## Run it

You need a JDK (17 or newer) and two libraries that are not in this repo. Put both jars in a `lib/` folder next to `src/`:

| Library | Where to get it |
|---|---|
| NetBeans AbsoluteLayout (`org.netbeans.lib.awtextra`) | Ships with the NetBeans IDE as `AbsoluteLayout.jar` |
| JFreeChart 1.5.x | https://www.jfree.org/jfreechart/ |

Then, from the repo root:

```bash
mkdir out
javac -encoding UTF-8 -cp "lib/*" -d out src/platezies/*.java
cp -r src/Images out/Images
java -cp "out:lib/*" platezies.Login      # on Windows use ; instead of :
```

Run it from the repo root, because the data files and menu photos are found by relative path. Alternatively, create a new Java project in NetBeans from the existing sources, add the two jars as libraries and run `platezies.Login`.

To log in as a customer, vendor or runner, use the **Register** screen on the login window first (passwords need 8 or more characters with upper case, lower case, a digit and a special character, and phone numbers are 10 digits). The built-in admin and manager accounts are defined in `Login.java`.

## Layout

```
src/platezies/        49 Java classes plus the NetBeans .form files
src/Images/           placeholder images (see below)
platezies_txtfiles/   data files; menu, orders, order items and delivery tasks hold sample data
```

## Changes from the submitted version

- Author name tags removed from the source headers.
- Sample data rebuilt: the menu is in the current five-column format (`cuisine,name,cost,price,image`; the cost column is set to 50% of price for the sample), delivery addresses replaced with generic ones, and absolute image paths replaced with relative ones.
- Fixed a folder-name typo (`platzies_txtfiles`) in `DeliverySystem.java` and replaced Windows-style `\\` path separators in seven files, so the app also runs on Linux and macOS.
- Logos, button icons and card images are generated placeholders, because the original artwork was not part of the files I kept. Replace the files in `src/Images/` with your own and the app will use them. Menu photos are not included, so the menu shows blank thumbnails.

## Known limitations

This was coursework, and a few things in it would not survive a security review. I have left them as they were and listed them here rather than hide them:

- Passwords are stored in plain text in the data files, and the admin and manager credentials are hard-coded in `Login.java`. A real system would store salted hashes (bcrypt or PBKDF2) and keep accounts out of the source.
- The payment screen is a simulation, but it writes the card number and security code to `payment.txt` in plain text. Real card data must never be handled this way, and the security code must never be stored at all.
- Records are comma-separated lines, so a comma typed into any field breaks that record. There is no file locking, so two instances writing at once can corrupt the data.
- There are no automated tests.

A natural next step would be to move storage to a database with parameterised queries, hash the passwords and drop the card fields.

## Licence

MIT, see [LICENSE](LICENSE).
