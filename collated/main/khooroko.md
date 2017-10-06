# khooroko
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath + "-backup.xml");
    }

```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public void backupAddressBook() throws IOException {
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional;
            ReadOnlyAddressBook initialData;
            addressBookOptional = readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("No backup will be made as data file does not exist");
            } else {
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
                saveAddressBook(initialData, getBackupAddressBookFilePath());
            }
        } catch (DataConversionException e) {
            logger.info("No backup will be made due to data file being in incorrect format");
        } catch (IOException e) {
            logger.info("No backup will be made due to problems while reading from file");
        }
    }

    @Override
    public ReadOnlyAddressBook getBestAvailableAddressBook() {
        ReadOnlyAddressBook initialData = new AddressBook();
        boolean dataFileIsOkay = false;
        Optional<ReadOnlyAddressBook> addressBookOptional;

        try {
            addressBookOptional = readAddressBook();
            if (addressBookOptional.isPresent()) {
                dataFileIsOkay = true;
            } else {
                logger.info("Data file not found");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file");
        }

        if (!dataFileIsOkay) {
            try {
                addressBookOptional = readBackupAddressBook();
                if (addressBookOptional.isPresent()) {
                    logger.info("Backup data file found. Will be starting with a backup");
                } else {
                    logger.info("Backup data file not found. Will be starting with a sample AddressBook");
                }
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            } catch (DataConversionException e) {
                logger.warning("Backup data file not in the correct format. Will be starting with an empty "
                        + "AddressBook");
            } catch (IOException e) {
                logger.warning("Problem while reading from the backup file. Will be starting with an empty "
                        + "AddressBook");
            }
        }
        return initialData;
    }

}
```
