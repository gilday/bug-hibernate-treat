# hibernate-treat-bug

Small project which isolates a bug in Hibernate of versions at least 4.3.11.Final

    # test fails due to hibernate bug using CriteriaBuilder#treat to downcast a Join then make a subsequent Join
    mvn clean test

    # upgrade Hibernate to 5.2.10.Final (the latest version as of writing) and the test passes 
    mvn -Dhibernate.version=5.2.10.Final clean test
