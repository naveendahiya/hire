(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListController', SavedListController);

    SavedListController.$inject = ['$scope', '$state', 'SavedList'];

    function SavedListController ($scope, $state, SavedList) {
        var vm = this;

        vm.savedLists = [];

        loadAll();

        function loadAll() {
            SavedList.query(function(result) {
                vm.savedLists = result;
            });
        }
    }
})();
