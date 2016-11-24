(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListEntryController', SavedListEntryController);

    SavedListEntryController.$inject = ['$scope', '$state', 'SavedListEntry'];

    function SavedListEntryController ($scope, $state, SavedListEntry) {
        var vm = this;

        vm.savedListEntries = [];

        loadAll();

        function loadAll() {
            SavedListEntry.query(function(result) {
                vm.savedListEntries = result;
            });
        }
    }
})();
