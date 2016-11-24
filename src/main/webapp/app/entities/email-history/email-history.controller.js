(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailHistoryController', EmailHistoryController);

    EmailHistoryController.$inject = ['$scope', '$state', 'EmailHistory'];

    function EmailHistoryController ($scope, $state, EmailHistory) {
        var vm = this;

        vm.emailHistories = [];

        loadAll();

        function loadAll() {
            EmailHistory.query(function(result) {
                vm.emailHistories = result;
            });
        }
    }
})();
