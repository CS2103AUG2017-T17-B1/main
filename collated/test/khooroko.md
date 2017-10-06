# khooroko
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void backupAddressBookReadSave() throws  Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        storageManager.backupAddressBook();
        ReadOnlyAddressBook retrieved = storageManager.readBackupAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getBackupAddressBookFilePath() {
        assertNotNull(storageManager.getBackupAddressBookFilePath());
    }

```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAndSaveBackupAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new backup and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readBackupAddressBook().get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting backup file, and read back
        original.addPerson(new Person(HOON));
        original.removePerson(new Person(ALICE));
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        readBack = xmlAddressBookStorage.readBackupAddressBook().get();
        assertEquals(original, new AddressBook(readBack));
    }

    @Test
    public void backupAddressBook_notXmlFormat_backupNotSaved () throws Exception {
        String filePath = testFolder.getRoot().getPath() + "NotXmlFormatAddressBook.xmm";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        xmlAddressBookStorage.backupAddressBook();
        assertEquals(Optional.empty(), xmlAddressBookStorage.readBackupAddressBook());
    }

    @Test
    public void getBestAvailableAddressBook_allInOrder_nonOptimal() throws Exception {
        AddressBook original = getTypicalAddressBook();
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
        original.addPerson(new Person(HOON));
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        File mainAddressBook;
        File backupAddressBook;
        // At this stage, main address book has one more person (HOON) than backup address book

        // Scenario 1: Main data file not found, use backup
        mainAddressBook = new File(xmlAddressBookStorage.getAddressBookFilePath());
        mainAddressBook.delete();
        assertEquals(new AddressBook(xmlAddressBookStorage.readBackupAddressBook().get()),
                new AddressBook(xmlAddressBookStorage.getBestAvailableAddressBook()));
        xmlAddressBookStorage.saveAddressBook(original, filePath);


        // Scenario 2: Main and backup data files both do not exist, use sample address book
        mainAddressBook = new File(xmlAddressBookStorage.getAddressBookFilePath());
        mainAddressBook.delete();
        backupAddressBook = new File(xmlAddressBookStorage.getBackupAddressBookFilePath());
        backupAddressBook.delete();
        assertEquals(new AddressBook(SampleDataUtil.getSampleAddressBook()),
                xmlAddressBookStorage.getBestAvailableAddressBook());
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        xmlAddressBookStorage.backupAddressBook();
    }

```
