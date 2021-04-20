根據pom.xml的dependency順序重新做:
1.Lifecycle:install dddcore
2.Lifecycle:install account、Lifecycle:install board
3.Lifecycle:install team
4.console: mvn package spring-boot:repackage // package monomain
//install是把module裝進maven local repository(C:\Users\ezKanban\.m2\repository)
//當code更新時應先將repository/ntut整個刪重新install掉再全部